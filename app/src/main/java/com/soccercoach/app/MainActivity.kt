package com.soccercoach.app

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (Prefs.notificationsEnabled(this)) scheduleDailyTip(this, Prefs.hour(this))
        setContent { CoachTheme { App() } }
    }
}

@Composable
fun CoachTheme(content: @Composable () -> Unit) {
    val colors = if (isSystemInDarkTheme()) {
        darkColorScheme(primary = Color(0xFF81C784), secondary = Color(0xFFFFC107))
    } else {
        lightColorScheme(primary = Color(0xFF1B5E20), secondary = Color(0xFFF9A825))
    }
    MaterialTheme(colorScheme = colors, content = content)
}

private val tabTitles = listOf("Home", "Formations", "Tactics", "Drills", "Settings")

@Composable
fun App() {
    val ctx = LocalContext.current
    var tab by remember { mutableIntStateOf(0) }
    var formation by remember { mutableStateOf<Formation?>(null) }
    var tactic by remember { mutableStateOf<Tactic?>(null) }
    var drill by remember { mutableStateOf<Drill?>(null) }
    var done by remember { mutableStateOf(Prefs.doneDrills(ctx)) }

    val inDetail = formation != null || tactic != null || drill != null
    BackHandler(enabled = inDetail) { formation = null; tactic = null; drill = null }

    Scaffold(
        bottomBar = {
            NavigationBar {
                val icons = listOf(Icons.Default.Home, Icons.Default.Star, Icons.Default.Info, Icons.Default.PlayArrow, Icons.Default.Settings)
                tabTitles.forEachIndexed { i, title ->
                    NavigationBarItem(
                        selected = tab == i && !inDetail,
                        onClick = { tab = i; formation = null; tactic = null; drill = null },
                        icon = { Icon(icons[i], contentDescription = title) },
                        label = { Text(title, fontSize = 11.sp) }
                    )
                }
            }
        }
    ) { pad ->
        Column(Modifier.fillMaxSize().padding(pad)) {
            when {
                formation != null -> FormationDetail(formation!!) { formation = null }
                tactic != null -> TacticDetail(tactic!!) { tactic = null }
                drill != null -> DrillDetail(drill!!, done.contains(drill!!.id),
                    onToggle = { done = Prefs.toggleDrill(ctx, drill!!.id) },
                    onBack = { drill = null })
                else -> when (tab) {
                    0 -> HomeScreen(done.size)
                    1 -> FormationsScreen { formation = it }
                    2 -> TacticsScreen { tactic = it }
                    3 -> DrillsScreen(done) { drill = it }
                    else -> SettingsScreen()
                }
            }
        }
    }
}

// ---------- shared pieces ----------

@Composable
fun Heading(text: String) {
    Text(text, style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold,
        modifier = Modifier.padding(16.dp, 16.dp, 16.dp, 8.dp))
}

@Composable
fun Section(title: String, items: List<String>) {
    Text(title, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold,
        modifier = Modifier.padding(top = 16.dp, bottom = 4.dp))
    items.forEachIndexed { i, s -> Text("•  $s", modifier = Modifier.padding(vertical = 2.dp)) }
}

@Composable
fun NumberedSection(title: String, items: List<String>) {
    Text(title, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold,
        modifier = Modifier.padding(top = 16.dp, bottom = 4.dp))
    items.forEachIndexed { i, s -> Text("${i + 1}.  $s", modifier = Modifier.padding(vertical = 2.dp)) }
}

@Composable
fun DetailPage(title: String, onBack: () -> Unit, content: @Composable () -> Unit) {
    Column(Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(16.dp)) {
        TextButton(onClick = onBack) { Text("← Back") }
        Text(title, style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold)
        content()
        Spacer(Modifier.height(24.dp))
    }
}

@Composable
fun PitchView(lines: List<Int>, modifier: Modifier = Modifier) {
    Canvas(modifier.fillMaxWidth().aspectRatio(0.72f).clip(RoundedCornerShape(12.dp))) {
        val w = size.width
        val h = size.height
        drawRect(Color(0xFF2E7D32))
        val stripe = h / 10
        for (i in 0 until 10 step 2) {
            drawRect(Color(0x14FFFFFF), Offset(0f, i * stripe), Size(w, stripe))
        }
        val line = Color.White.copy(alpha = 0.85f)
        val stroke = Stroke(width = 3f)
        val m = 16f
        drawRect(line, Offset(m, m), Size(w - 2 * m, h - 2 * m), style = stroke)
        drawLine(line, Offset(m, h / 2), Offset(w - m, h / 2), strokeWidth = 3f)
        drawCircle(line, w * 0.13f, Offset(w / 2, h / 2), style = stroke)
        val bw = w * 0.5f
        val bh = h * 0.14f
        drawRect(line, Offset((w - bw) / 2, m), Size(bw, bh), style = stroke)
        drawRect(line, Offset((w - bw) / 2, h - m - bh), Size(bw, bh), style = stroke)

        val r = w * 0.035f
        fun dot(x: Float, y: Float, c: Color) {
            drawCircle(c, r, Offset(x * w, y * h))
            drawCircle(Color.White, r, Offset(x * w, y * h), style = Stroke(2f))
        }
        dot(0.5f, 0.92f, Color(0xFFFFC107)) // goalkeeper
        val n = lines.size
        lines.forEachIndexed { i, count ->
            val y = if (n == 1) 0.5f else 0.76f - 0.62f * i / (n - 1)
            for (j in 0 until count) dot((j + 1f) / (count + 1f), y, Color(0xFF1565C0))
        }
    }
}

// ---------- screens ----------

@Composable
fun HomeScreen(doneCount: Int) {
    val tip = remember { tipOfTheDay() }
    var expanded by remember { mutableIntStateOf(-1) }
    LazyColumn(contentPadding = PaddingValues(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
        item {
            Text("Soccer Coach", style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold)
        }
        item {
            Card(colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)) {
                Column(Modifier.padding(16.dp)) {
                    Text("TIP OF THE DAY", style = MaterialTheme.typography.labelMedium)
                    Text(tip.title, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
                    Spacer(Modifier.height(6.dp))
                    Text(tip.body)
                }
            }
        }
        item {
            Card(Modifier.fillMaxWidth()) {
                Column(Modifier.padding(16.dp)) {
                    Text("Drill progress", style = MaterialTheme.typography.titleMedium)
                    Spacer(Modifier.height(8.dp))
                    LinearProgressIndicator(progress = { doneCount.toFloat() / drills.size }, modifier = Modifier.fillMaxWidth())
                    Spacer(Modifier.height(6.dp))
                    Text("$doneCount of ${drills.size} drills completed")
                }
            }
        }
        item { Text("All tips", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.SemiBold) }
        items(tips.size) { i ->
            Card(Modifier.fillMaxWidth().clickable { expanded = if (expanded == i) -1 else i }) {
                Column(Modifier.padding(14.dp)) {
                    Text(tips[i].title, fontWeight = FontWeight.SemiBold)
                    if (expanded == i) {
                        Spacer(Modifier.height(4.dp))
                        Text(tips[i].body)
                    }
                }
            }
        }
    }
}

@Composable
fun FormationsScreen(onOpen: (Formation) -> Unit) {
    Column {
        Heading("Formations")
        LazyColumn(contentPadding = PaddingValues(16.dp, 0.dp, 16.dp, 16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
            items(formations) { f ->
                Card(Modifier.fillMaxWidth().clickable { onOpen(f) }) {
                    Column(Modifier.padding(16.dp)) {
                        Text(f.name, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
                        Text(f.style, color = MaterialTheme.colorScheme.primary)
                        Spacer(Modifier.height(4.dp))
                        Text(f.summary, maxLines = 2)
                    }
                }
            }
        }
    }
}

@Composable
fun FormationDetail(f: Formation, onBack: () -> Unit) {
    DetailPage(f.name, onBack) {
        Text(f.style, color = MaterialTheme.colorScheme.primary)
        Spacer(Modifier.height(12.dp))
        PitchView(f.lines, Modifier.padding(horizontal = 24.dp))
        Spacer(Modifier.height(12.dp))
        Text(f.summary)
        Section("Strengths", f.strengths)
        Section("Weaknesses", f.weaknesses)
        Section("Player roles", f.roleTips)
    }
}

@Composable
fun TacticsScreen(onOpen: (Tactic) -> Unit) {
    Column {
        Heading("Tactics")
        LazyColumn(contentPadding = PaddingValues(16.dp, 0.dp, 16.dp, 16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
            items(tactics) { t ->
                Card(Modifier.fillMaxWidth().clickable { onOpen(t) }) {
                    Column(Modifier.padding(16.dp)) {
                        Text(t.title, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                        Text(t.tagline, color = MaterialTheme.colorScheme.primary)
                    }
                }
            }
        }
    }
}

@Composable
fun TacticDetail(t: Tactic, onBack: () -> Unit) {
    DetailPage(t.title, onBack) {
        Text(t.tagline, color = MaterialTheme.colorScheme.primary)
        Spacer(Modifier.height(12.dp))
        Text(t.explanation)
        NumberedSection("How to do it", t.howToDoIt)
        Spacer(Modifier.height(16.dp))
        Card(colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer)) {
            Column(Modifier.padding(14.dp)) {
                Text("Forward's tip", fontWeight = FontWeight.Bold)
                Text(t.forwardTip)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DrillsScreen(done: Set<String>, onOpen: (Drill) -> Unit) {
    var category by remember { mutableStateOf("All") }
    val shown = if (category == "All") drills else drills.filter { it.category == category }
    Column {
        Heading("Drills")
        LazyRow(contentPadding = PaddingValues(horizontal = 16.dp), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            items(drillCategories) { c ->
                FilterChip(selected = category == c, onClick = { category = c }, label = { Text(c) })
            }
        }
        Spacer(Modifier.height(8.dp))
        LazyColumn(contentPadding = PaddingValues(16.dp, 4.dp, 16.dp, 16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
            items(shown) { d ->
                Card(Modifier.fillMaxWidth().clickable { onOpen(d) }) {
                    Row(Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                        Column(Modifier.weight(1f)) {
                            Text(d.title, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                            Text("${d.category} • ${d.minutes} min • ${d.level}", color = MaterialTheme.colorScheme.primary)
                        }
                        if (done.contains(d.id)) Text("✓ Done", color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}

@Composable
fun DrillDetail(d: Drill, isDone: Boolean, onToggle: () -> Unit, onBack: () -> Unit) {
    DetailPage(d.title, onBack) {
        Text("${d.category} • ${d.minutes} min • ${d.level}", color = MaterialTheme.colorScheme.primary)
        Section("Setup", listOf(d.setup))
        NumberedSection("Steps", d.steps)
        Section("Coaching points", d.coachingPoints)
        Spacer(Modifier.height(20.dp))
        if (isDone) {
            OutlinedButton(onClick = onToggle, modifier = Modifier.fillMaxWidth()) { Text("✓ Completed (tap to undo)") }
        } else {
            Button(onClick = onToggle, modifier = Modifier.fillMaxWidth()) { Text("Mark as done") }
        }
    }
}

@Composable
fun SettingsScreen() {
    val ctx = LocalContext.current
    var enabled by remember { mutableStateOf(Prefs.notificationsEnabled(ctx)) }
    var hour by remember { mutableIntStateOf(Prefs.hour(ctx)) }

    fun hasPermission(c: Context) = Build.VERSION.SDK_INT < 33 ||
        ContextCompat.checkSelfPermission(c, Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED

    fun enable() {
        enabled = true
        Prefs.setNotificationsEnabled(ctx, true)
        scheduleDailyTip(ctx, hour)
    }

    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission()) { granted ->
        if (granted) enable()
    }

    Column(Modifier.fillMaxSize().verticalScroll(rememberScrollState())) {
        Heading("Settings")
        Column(Modifier.padding(horizontal = 16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Column(Modifier.weight(1f)) {
                    Text("Daily coaching tip", style = MaterialTheme.typography.titleMedium)
                    Text("Get one tip in your notification bar every day")
                }
                Switch(checked = enabled, onCheckedChange = { on ->
                    if (on) {
                        if (hasPermission(ctx)) enable() else launcher.launch(Manifest.permission.POST_NOTIFICATIONS)
                    } else {
                        enabled = false
                        Prefs.setNotificationsEnabled(ctx, false)
                        cancelDailyTip(ctx)
                    }
                })
            }
            Spacer(Modifier.height(20.dp))
            Text("Reminder time", style = MaterialTheme.typography.titleMedium)
            Row(verticalAlignment = Alignment.CenterVertically) {
                OutlinedButton(onClick = {
                    hour = (hour + 23) % 24
                    Prefs.setHour(ctx, hour)
                    if (enabled) scheduleDailyTip(ctx, hour)
                }) { Text("−") }
                Text("%02d:00".format(hour), style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier.padding(horizontal = 20.dp))
                OutlinedButton(onClick = {
                    hour = (hour + 1) % 24
                    Prefs.setHour(ctx, hour)
                    if (enabled) scheduleDailyTip(ctx, hour)
                }) { Text("+") }
            }
            Spacer(Modifier.height(20.dp))
            Button(onClick = {
                if (hasPermission(ctx)) sendTestTip(ctx) else launcher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }) { Text("Send a test notification now") }
        }
    }
}
