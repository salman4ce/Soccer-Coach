package com.soccercoach.app

data class Formation(
    val name: String,
    val lines: List<Int>,          // defence -> attack (goalkeeper is added automatically)
    val style: String,
    val summary: String,
    val strengths: List<String>,
    val weaknesses: List<String>,
    val roleTips: List<String>
)

data class Tactic(
    val title: String,
    val tagline: String,
    val explanation: String,
    val howToDoIt: List<String>,
    val forwardTip: String
)

data class Drill(
    val id: String,
    val title: String,
    val category: String,
    val minutes: Int,
    val level: String,
    val setup: String,
    val steps: List<String>,
    val coachingPoints: List<String>
)

data class Tip(val title: String, val body: String)

val drillCategories = listOf("All", "Finishing", "Dribbling", "Passing", "Movement", "Defending", "Fitness", "Set pieces")

val formations = listOf(
    Formation(
        "4-3-3", listOf(4, 3, 3), "Attacking / Possession",
        "Three forwards stretch the back line while a midfield trio controls the centre. The base of most modern pressing and possession teams.",
        listOf("Width and depth in attack", "Easy passing triangles", "Strong high press"),
        listOf("Wide gaps if fullbacks push up", "Lone holding midfielder can be overloaded"),
        listOf("Striker: pin the centre-backs and make runs in behind", "Wingers: stay wide, then attack the far post on crosses", "No.6: shield the back four and show for the ball")
    ),
    Formation(
        "4-4-2", listOf(4, 4, 2), "Balanced / Direct",
        "Two banks of four with a strike partnership. Compact, simple to learn and very hard to break down.",
        listOf("Compact and organised", "Two strikers work as a pair", "Good for crosses and counters"),
        listOf("Can be outnumbered in midfield", "Needs hard-working wide midfielders"),
        listOf("Strikers: one drops short, one runs in behind", "Wide midfielders: track back and support fullbacks", "Centre mids: stay connected, never both forward")
    ),
    Formation(
        "4-2-3-1", listOf(4, 2, 3, 1), "Balanced / Flexible",
        "Two holding midfielders protect the defence while a No.10 links to a lone striker. Very popular at every level.",
        listOf("Solid defensive base", "Creative No.10 behind the striker", "Quick switch between attack and defence"),
        listOf("Lone striker can be isolated", "Relies on a good No.10"),
        listOf("Striker: hold the ball up and bring the No.10 into play", "No.10: find pockets between the lines", "Double pivot: one covers, one distributes")
    ),
    Formation(
        "3-5-2", listOf(3, 5, 2), "Midfield control / Wing-backs",
        "Three centre-backs, wing-backs providing width and a crowded midfield with two strikers up top.",
        listOf("Midfield overload", "Two strikers to attack with", "Wing-backs give width"),
        listOf("Space behind wing-backs", "Needs very fit wing-backs"),
        listOf("Wing-backs: time your overlaps, recover fast", "Strikers: split the centre-backs", "Middle centre-back: sweep and start attacks")
    ),
    Formation(
        "3-4-3", listOf(3, 4, 3), "Very attacking / High press",
        "Three at the back with a front three. Great for dominant teams that want to press high and win it back near the goal.",
        listOf("Lots of attackers", "Strong pressing shape", "Natural passing triangles"),
        listOf("Exposed to counter-attacks", "Two-man midfield can be outnumbered"),
        listOf("Wide forwards: cut inside onto your stronger foot", "Wing-backs: provide all the width", "Centre-backs: step into midfield with the ball")
    ),
    Formation(
        "5-3-2", listOf(5, 3, 2), "Defensive / Counter-attack",
        "Five defenders and three midfielders protect the goal, with two strikers waiting to counter.",
        listOf("Very hard to break down", "Great against stronger teams", "Strong on counters"),
        listOf("Can be too passive", "Strikers often isolated"),
        listOf("Strikers: stay high, be a target for long balls", "Wing-backs: pick moments to push up", "Midfield: protect the space in front of the back five")
    ),
    Formation(
        "4-1-4-1", listOf(4, 1, 4, 1), "Defensive / Compact",
        "A single holding midfielder shields the defence behind a flat midfield four. Compact and tough to play through.",
        listOf("Strong central protection", "Four-man midfield covers the width", "Good base for counters"),
        listOf("Lone striker can be cut off", "Holding mid under pressure"),
        listOf("No.6: dictate tempo, drop between centre-backs", "Wide mids: support the striker in the box", "Striker: win aerial duels and hold up play")
    ),
    Formation(
        "4-3-2-1", listOf(4, 3, 2, 1), "Central / Narrow",
        "The 'Christmas tree'. Two attacking midfielders support a lone striker through the centre.",
        listOf("Dense in the middle", "Two creators behind the striker", "Fullbacks give the width"),
        listOf("Lacks natural width", "Relies on fullback energy"),
        listOf("Inside forwards: drift into half-spaces", "Striker: run the channels to pull defenders out", "Fullbacks: overlap constantly")
    ),
    Formation(
        "4-4-1-1", listOf(4, 4, 1, 1), "Balanced / Counter",
        "A 4-4-2 variation where the second striker drops off to link play. Compact with a creative edge.",
        listOf("Compact two banks of four", "Link player between midfield and attack", "Good defensive shape"),
        listOf("Only one true striker", "Second striker must work in both phases"),
        listOf("Second striker: find space between the lines", "Striker: make runs beyond the defence", "Midfield four: slide together as a unit")
    ),
    Formation(
        "3-4-1-2", listOf(3, 4, 1, 2), "Central / Creative",
        "Three centre-backs, wing-backs, a playmaker and two strikers: a flexible system built on a strong central core.",
        listOf("Strong central presence", "Playmaker gets time on the ball", "Two strikers to finish"),
        listOf("Wide areas rely on wing-backs", "Can leave gaps if the No.10 doesn't track back"),
        listOf("Playmaker: operate between the lines", "Strikers: combine with one-twos", "Wing-backs: stay disciplined")
    )
)

val tactics = listOf(
    Tactic(
        "High Press (Gegenpressing)", "Win the ball back within 5 seconds of losing it",
        "The moment the ball is lost, the nearest players swarm the ball carrier to win it back near the opponent's goal, before their defence is organised.",
        listOf("Set a pressing trigger: a bad touch, a back-pass or a pass to a fullback", "Press as a unit, with the nearest player closing fast and others cutting passing lanes", "Cut off the pass, not just the player", "If the press is beaten, drop quickly into shape"),
        "As a forward you lead the press. Curve your run so the defender can only pass where your teammates are waiting."
    ),
    Tactic(
        "Low Block", "Stay compact and deny space behind",
        "Defend deep with two tight banks of players so the opponent has no room to play through you.",
        listOf("Keep the distance between lines under 15 metres", "Shift as a team to the side of the ball", "Protect the penalty area and the centre", "Stay patient, don't dive in"),
        "Even in a low block, keep one forward high as an outlet. It keeps their defenders from pushing up."
    ),
    Tactic(
        "Counter-Attack", "Turn defence into attack in seconds",
        "Win the ball, then attack at speed before the opponent can reset their shape.",
        listOf("First pass forward, ideally to a player who is facing goal", "Runners go wide and in behind, not all at once to the ball", "Aim to get to a shot within 8 to 10 seconds", "Support runners arrive late into the box"),
        "Start your run the moment your team wins possession, not once the ball is played."
    ),
    Tactic(
        "Possession Play (Tiki-Taka)", "Control the game by keeping the ball",
        "Short quick passing to pull the opponent out of shape and open gaps.",
        listOf("Always offer two passing options to the player on the ball", "Play in triangles", "Use one- and two-touch passes", "Move after you pass"),
        "Drop into pockets of space to link with midfielders, then spin in behind."
    ),
    Tactic(
        "Offside Trap", "Step up together to catch attackers",
        "The back line steps up in unison to leave the attacker in an offside position at the moment the pass is played.",
        listOf("One leader (usually the centre-back) calls the step", "Step up as the ball is struck, not after", "Everyone moves together, and one player staying back breaks it", "Drop immediately if the ball is played over the top"),
        "Stay on the shoulder of the last defender and time your run just after the pass is played."
    ),
    Tactic(
        "Overlap and Underlap", "Create 2v1s on the flanks",
        "A fullback runs past (overlap) or inside (underlap) the winger to create an extra player out wide.",
        listOf("Winger holds the ball and drags the fullback in", "Fullback runs on the outside for an overlap", "Winger can cut inside or play the ball into the runner's path", "Cross early or cut back across goal"),
        "Be ready at the far post or the edge of the box for the cut-back."
    ),
    Tactic(
        "Playing Out From the Back", "Build from the goalkeeper",
        "Start attacks from your own goalkeeper and defenders to draw the press, then play through the gaps.",
        listOf("Goalkeeper and centre-backs spread wide", "Midfielder drops to create a passing option", "Bait the press, then play through or over it", "Know when to go long if every option is blocked"),
        "Stay high and pin the opposition defenders, so they can't step forward to press."
    ),
    Tactic(
        "Half-Space Attacking", "Use the channel between the winger and the centre",
        "The half-spaces are the vertical lanes between the wide area and the centre. Players there are hard to mark.",
        listOf("Attackers receive on the half-turn between lines", "Wide player stays wide to stretch the defence", "Combine with one-twos and third-man runs", "Shoot or play a through-ball from there"),
        "Receive on the half-turn so you can face goal immediately."
    ),
    Tactic(
        "Third-Man Runs", "Break lines with a run nobody expected",
        "Player A passes to B, who lays it off to C running into the space behind the defence.",
        listOf("A plays into B with a pass that invites a lay-off", "B plays first-time into the path of C", "C starts running as A passes, not after B", "Works best against a man-marking defence"),
        "Ghost into the box from deep as the third man and you'll be free of your marker."
    ),
    Tactic(
        "Transition (Defence to Attack)", "The first 3 seconds after winning the ball",
        "The moments after a turnover are when the opponent is most disorganised.",
        listOf("Look up before you win the ball", "Make the first pass forward or to the free player", "Forwards stay high to give an outlet", "Don't dribble into traffic"),
        "Stay on the shoulder of the last defender so you're an option for the first pass."
    ),
    Tactic(
        "Set Piece Routines", "Win games from dead balls",
        "Corners, free kicks and throw-ins decide many matches. Rehearsed routines create goals.",
        listOf("Attacking corners: decoy runs, block the keeper, attack the near post", "Defending corners: mix zonal and man-marking, with a player at each post", "Free kicks: use a decoy runner and vary your delivery", "Quick throw-ins to catch the defence sleeping"),
        "Attack the space, not the ball. Time your run so you meet the delivery at full speed."
    ),
    Tactic(
        "False 9", "A striker who drops deep to drag defenders out",
        "The centre-forward drops into midfield, pulling a centre-back with them and creating space for runners.",
        listOf("Drop to receive between the lines", "Wingers and midfielders run into the space left behind", "Combine with quick wall passes", "Turn and drive at the defence"),
        "If you drop and the centre-back doesn't follow, turn and run at them. If he does, play it into the space he leaves."
    )
)

val drills = listOf(
    Drill("fin_1", "Cut-Back Finishing", "Finishing", 15, "Beginner",
        "Cones on the edge of the box. A server on the byline. 8-10 balls.",
        listOf("Server plays a pass back to the edge of the box", "Run onto the ball and hit it first time", "Alternate sides and finish with each foot", "Score 8 out of 10 to move on"),
        listOf("Plant foot beside the ball", "Keep your head steady", "Aim for the corners, not at the keeper")),
    Drill("fin_2", "1v1 Against the Keeper", "Finishing", 15, "Intermediate",
        "Half the penalty area, a goalkeeper and a line of balls 25m out.",
        listOf("Dribble at speed towards the goal", "Decide early: shoot, round the keeper or chip", "Finish within 3 touches", "Reset quickly and repeat 10 times"),
        listOf("Stay calm and pick your spot", "Use the inside of your foot for accuracy", "Look at the keeper's body shape")),
    Drill("fin_3", "Finishing From Crosses", "Finishing", 20, "Intermediate",
        "Two crossers on each wing. A defender in the box. Balls on both sides.",
        listOf("Start at the edge of the box and make a curved run", "Attack the near post, then the far post", "Volley, header or side-foot finish", "Rotate every 5 balls"),
        listOf("Change direction to lose your marker", "Attack the ball, don't wait for it", "Keep your head down at the point of contact")),
    Drill("fin_4", "Quick-Fire Shooting", "Finishing", 10, "Beginner",
        "Five balls in an arc 18 yards from goal.",
        listOf("Hit each ball in sequence with no pause", "Mix your technique: laces, side-foot, curl", "Count how many are on target", "Try to beat your score each time"),
        listOf("Pick your target before every shot", "Strike through the middle of the ball", "Follow through towards the target")),
    Drill("fin_5", "Back-to-Goal Hold-Up Play", "Finishing", 15, "Advanced",
        "Server, passive defender and a goal.",
        listOf("Receive with your back to goal and a defender on you", "Use your body to shield the ball", "Lay off to a runner or spin and shoot", "Repeat from both sides"),
        listOf("Arm out for balance, but don't push", "Keep the ball on your far foot", "Check your shoulder before you receive")),
    Drill("dri_1", "Cone Weave", "Dribbling", 10, "Beginner",
        "Eight cones in a line, 1 metre apart.",
        listOf("Weave through using the inside of each foot", "Repeat using only the outside of your foot", "Try with your weaker foot only", "Finish with a sprint"),
        listOf("Keep the ball close", "Head up between touches", "Bend your knees and stay low")),
    Drill("dri_2", "1v1 Boxes", "Dribbling", 15, "Intermediate",
        "A 10m x 10m grid with a goal at each end.",
        listOf("Attacker takes on the defender to score", "Use feints, step-overs and speed changes", "Switch roles after every attempt", "First to 5 wins"),
        listOf("Attack the defender's front foot", "Explode past after the move", "Protect the ball with your body")),
    Drill("dri_3", "Close Control Box", "Dribbling", 10, "Beginner",
        "A 5m x 5m square and one ball.",
        listOf("Dribble inside using only the soles", "Add inside and outside cuts", "Try changing direction on a signal", "Do 4 rounds of 45 seconds"),
        listOf("Small, quick touches", "Look up often", "Use both feet")),
    Drill("pas_1", "Passing Squares", "Passing", 15, "Beginner",
        "Four cones in a 10m square, one player at each cone.",
        listOf("Pass and follow your pass to the next cone", "Two-touch passing, then one-touch", "Reverse the direction", "Add a second ball for a challenge"),
        listOf("Lock your ankle and strike the middle of the ball", "Receive on the back foot", "Move straight after passing")),
    Drill("pas_2", "Rondo 4v1", "Passing", 15, "Intermediate",
        "A 8m x 8m square with four players on the outside and one defender in the middle.",
        listOf("Keep possession with a maximum of two touches", "The defender who wins the ball swaps with the player who lost it", "Progress to 5v2", "Count how many consecutive passes you can string together"),
        listOf("Open your body to see the whole square", "Pass away from the defender", "Make small movements to create angles")),
    Drill("pas_3", "Long Ball Switches", "Passing", 15, "Intermediate",
        "Two players 30m apart, with cones marking the target zones.",
        listOf("Hit a driven pass to your partner's feet", "Then a lofted ball to their chest", "Alternate feet", "Add a moving target"),
        listOf("Lean back slightly for the lofted ball", "Follow through towards the target", "Strike the lower half of the ball for height")),
    Drill("pas_4", "Wall-Pass Combinations", "Passing", 15, "Intermediate",
        "A line of cones, a partner as the 'wall' and balls.",
        listOf("Pass to the wall and sprint past the cone", "Receive the return pass in your stride", "Finish with a shot", "Rotate positions every 6 reps"),
        listOf("Play the pass with pace", "Run into the space behind the cone", "Receive on the move")),
    Drill("mov_1", "Runs in Behind", "Movement", 15, "Intermediate",
        "A midfielder with balls, a defender and a forward on the halfway line.",
        listOf("Check towards the midfielder, then spin in behind", "Time the run so you stay onside", "Take the through-ball in your stride and finish", "Repeat on both sides"),
        listOf("Sell the fake first", "Watch the ball and the line together", "Accelerate after the pass is played")),
    Drill("mov_2", "Movement in the Box", "Movement", 15, "Advanced",
        "Two crossers, two forwards and a defender.",
        listOf("Forwards start near the penalty spot", "Break away from the defender with a double movement", "Attack different zones: near post, far post, penalty spot", "Finish first time"),
        listOf("Disguise your run", "Be decisive and attack the ball", "Never stand still in the box")),
    Drill("mov_3", "Receiving and Turning", "Movement", 12, "Beginner",
        "Server, a cone and a small goal.",
        listOf("Check away from the cone, then receive", "Take a first touch to turn away from pressure", "Accelerate away with the ball", "Repeat with the left and right foot"),
        listOf("Look over your shoulder before the pass", "Use a touch that moves the ball away from pressure", "Open your hips to the space")),
    Drill("def_1", "1v1 Defending", "Defending", 15, "Intermediate",
        "A 15m x 10m channel with a small goal at one end.",
        listOf("Close the attacker quickly, then slow down", "Stay side-on and force them to one side", "Tackle only when you're confident", "Swap roles after each turn"),
        listOf("Stay on your toes", "Watch the ball, not the feet", "Be patient and make the attacker make a mistake")),
    Drill("def_2", "Defensive Shape Shuffle", "Defending", 15, "Intermediate",
        "Four defenders in a line, a coach with the ball.",
        listOf("Slide left and right as the coach moves the ball", "Keep an even distance between players", "Step up when the ball is played backwards", "Drop when the ball goes past you"),
        listOf("Communicate constantly", "Keep the line straight", "Stay compact")),
    Drill("def_3", "Pressing Triggers", "Defending", 20, "Advanced",
        "Half a pitch, 6 vs 6 and a goal.",
        listOf("Defenders sit in a medium block", "Press as a unit when the ball goes to the fullback", "Cut the passing lanes as you press", "Switch when a press is beaten"),
        listOf("Press with intensity and at an angle", "Everyone presses together or nobody does", "Recover fast if the press is broken")),
    Drill("fit_1", "Sprint Ladder", "Fitness", 15, "Intermediate",
        "Cones at 10m, 20m and 30m.",
        listOf("Sprint to 10m and walk back", "Sprint to 20m and walk back", "Sprint to 30m and walk back", "Rest 90 seconds and repeat 4 rounds"),
        listOf("Drive with your arms", "Stay tall and run on your toes", "Give full effort on every sprint")),
    Drill("fit_2", "Agility Ladder Footwork", "Fitness", 10, "Beginner",
        "An agility ladder or chalked boxes on the ground.",
        listOf("One foot in each box", "Two feet in each box", "Lateral in-and-out steps", "Finish with a 10m sprint"),
        listOf("Quick light feet", "Stay on the balls of your feet", "Look up, not at your feet")),
    Drill("fit_3", "Interval Shuttle Runs", "Fitness", 15, "Advanced",
        "Two cones 20m apart.",
        listOf("Sprint between the cones for 30 seconds", "Rest for 30 seconds", "Repeat 10 times", "Cool down with a light jog"),
        listOf("Turn quickly and push off hard", "Control your breathing", "Keep the same pace every round")),
    Drill("set_1", "Penalty Routine", "Set pieces", 10, "Beginner",
        "A goal with a goalkeeper and 10 balls.",
        listOf("Pick a corner before you step up", "Take a consistent run-up", "Place the ball, don't blast it", "Score 8 out of 10"),
        listOf("Stay calm and breathe", "Do not change your mind mid-run-up", "Keep your head down through the strike")),
    Drill("set_2", "Free-Kick Technique", "Set pieces", 15, "Intermediate",
        "Balls 20m from goal and a wall of cones.",
        listOf("Practise a curled shot over the wall", "Try a driven shot below the wall", "Aim for each top corner", "Switch the position every 3 shots"),
        listOf("Strike the side of the ball for curl", "Plant foot beside the ball and lean back slightly", "Follow through across your body"))
)

val tips = listOf(
    Tip("Scan before you receive", "Look over your shoulder 2 or 3 times before the ball arrives. Players who scan make better first touches and faster decisions."),
    Tip("Take your first touch forward", "Use your first touch to move the ball away from pressure and towards the goal, not just to stop it."),
    Tip("Move after you pass", "A pass isn't the end of your involvement. Move into a new space so you're an option again straight away."),
    Tip("Practise your weaker foot", "Spend 10 minutes of every session using only your weaker foot. It makes you unpredictable and harder to defend."),
    Tip("Pick your spot early", "Before you shoot, decide where the ball is going. Hesitation is the most common reason for a missed chance."),
    Tip("Make a double movement", "As a forward, check away first and then spin the other way. It gives you the half-yard you need to lose your marker."),
    Tip("Stay on the last defender's shoulder", "Positioning yourself on the shoulder of the last defender keeps you onside and ready to run in behind."),
    Tip("Attack the near post", "Many goals come from a run to the near post. Defenders often turn their heads towards the ball and lose you."),
    Tip("Communicate constantly", "Call for the ball, tell teammates where the pressure is, and organise the team. Talking wins games."),
    Tip("Body shape matters", "Receive the ball with your body half-turned so you can see the whole pitch and play forward immediately."),
    Tip("Don't dive in", "When defending, stay on your feet. Make the attacker make the mistake rather than committing too early."),
    Tip("Win the ball back quickly", "After you lose the ball, you have around 5 seconds to win it back before the opponent is organised. Press immediately."),
    Tip("Play the percentages", "If you're not sure, play the safe pass. Keeping possession is better than losing it in a dangerous area."),
    Tip("Fitness is a skill", "Skills only matter if you can repeat them in the 90th minute. Add sprint intervals twice a week."),
    Tip("Watch professionals", "Watch a match and focus only on one player in your position for 10 minutes. Notice their movement without the ball."),
    Tip("Warm up properly", "Spend 10 minutes on dynamic stretches, light jogging and short passes before you play. It reduces injuries and sharpens your touch."),
    Tip("Hydrate before you're thirsty", "Drink water throughout the day, not just during training. Even mild dehydration cuts your focus and your sprint speed."),
    Tip("Rest is part of training", "Sleep 8 hours and take at least one rest day each week. Muscles improve when they recover, not when they're being worked."),
    Tip("Be patient in the final third", "Don't rush chances. Take a touch, look up and pick the right option. A calm player scores more often."),
    Tip("Practise under pressure", "Train with a defender or a time limit. Skills that only work with unlimited time won't work in a match."),
    Tip("Use both feet to cut inside", "A winger who can cut in on either foot is twice as dangerous. Practise cutting in from both sides."),
    Tip("Set small goals each session", "Focus on one thing, like a first touch or scanning, in each session. Improvement is built in small steps."),
    Tip("Reset after a mistake", "Everyone makes mistakes. Forget it within 5 seconds and focus on the next action. Dwelling on errors leads to more errors."),
    Tip("Create angles", "Always show for the ball at an angle, never in a straight line behind a teammate. It makes the pass easier."),
    Tip("Shield the ball", "Put your body between the defender and the ball, use your arm for balance and keep the ball on the far foot."),
    Tip("Think one pass ahead", "Before the ball arrives, know what you'll do with it. The best players already have their next move planned."),
    Tip("Know your role in the formation", "Every position has specific jobs when you have the ball and when you don't. Learn both."),
    Tip("Eat for performance", "Eat carbohydrates 2 to 3 hours before playing, and protein within an hour afterwards to help recovery."),
    Tip("Finish with composure", "Slow your heart rate with a deep breath before a one-on-one. Pick your side and place the ball."),
    Tip("Review your game", "After a match, write down 3 things you did well and 1 thing to improve. Over a season, this builds real progress."),
    Tip("Visualise success", "Spend 5 minutes before a match imagining yourself receiving the ball, turning and scoring. It builds confidence and clarity.")
)
