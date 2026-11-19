package com.soccercoach.app

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.Worker
import androidx.work.WorkerParameters
import java.util.Calendar
import java.util.concurrent.TimeUnit

private const val CHANNEL_ID = "daily_tip"
private const val WORK_NAME = "daily_tip_work"

/** Picks a tip based on the day of the year so it changes every day. */
fun tipOfTheDay(): Tip {
    val day = Calendar.getInstance().get(Calendar.DAY_OF_YEAR)
    return tips[day % tips.size]
}

class TipWorker(private val ctx: Context, params: WorkerParameters) : Worker(ctx, params) {
    override fun doWork(): Result {
        showTipNotification(ctx, tipOfTheDay())
        return Result.success()
    }
}

fun showTipNotification(ctx: Context, tip: Tip) {
    if (Build.VERSION.SDK_INT >= 33 &&
        ContextCompat.checkSelfPermission(ctx, Manifest.permission.POST_NOTIFICATIONS)
        != PackageManager.PERMISSION_GRANTED
    ) return

    val manager = ctx.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    if (Build.VERSION.SDK_INT >= 26) {
        manager.createNotificationChannel(
            NotificationChannel(CHANNEL_ID, "Daily coaching tip", NotificationManager.IMPORTANCE_DEFAULT)
        )
    }

    val open = PendingIntent.getActivity(
        ctx, 0, Intent(ctx, MainActivity::class.java),
        PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
    )

    val notification = NotificationCompat.Builder(ctx, CHANNEL_ID)
        .setSmallIcon(R.drawable.ic_ball)
        .setContentTitle("Today's tip: ${tip.title}")
        .setContentText(tip.body)
        .setStyle(NotificationCompat.BigTextStyle().bigText(tip.body))
        .setContentIntent(open)
        .setAutoCancel(true)
        .build()

    NotificationManagerCompat.from(ctx).notify(1001, notification)
}

/** Schedules a repeating 24h job whose first run is at the next [hour]:00. */
fun scheduleDailyTip(ctx: Context, hour: Int) {
    val now = Calendar.getInstance()
    val next = Calendar.getInstance().apply {
        set(Calendar.HOUR_OF_DAY, hour)
        set(Calendar.MINUTE, 0)
        set(Calendar.SECOND, 0)
        set(Calendar.MILLISECOND, 0)
        if (before(now)) add(Calendar.DAY_OF_YEAR, 1)
    }
    val delay = next.timeInMillis - now.timeInMillis

    val request = PeriodicWorkRequestBuilder<TipWorker>(24, TimeUnit.HOURS)
        .setInitialDelay(delay, TimeUnit.MILLISECONDS)
        .build()

    WorkManager.getInstance(ctx)
        .enqueueUniquePeriodicWork(WORK_NAME, ExistingPeriodicWorkPolicy.UPDATE, request)
}

fun cancelDailyTip(ctx: Context) {
    WorkManager.getInstance(ctx).cancelUniqueWork(WORK_NAME)
}

fun sendTestTip(ctx: Context) {
    WorkManager.getInstance(ctx).enqueue(OneTimeWorkRequestBuilder<TipWorker>().build())
}
