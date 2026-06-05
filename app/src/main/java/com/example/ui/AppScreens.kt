package com.example.ui
import androidx.compose.animation.core.*
import androidx.compose.animation.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.*
import com.example.ui.theme.*
import androidx.compose.runtime.collectAsState
import kotlinx.coroutines.launch

// ==========================================
// 1. LOGIN SCREEN
// ==========================================
@Composable
fun LoginScreen(viewModel: MainViewModel) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(FF_Dark_Bg, Color(0xFF1E1430), FF_Dark_Bg)
                )
            )
            .padding(24.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
        ) {
            // Game Styled Logo
            GameLogoHeader()

            Spacer(modifier = Modifier.height(32.dp))

            Surface(
                color = FF_Card_Bg,
                shape = RoundedCornerShape(16.dp),
                border = BorderStroke(1.dp, FF_Orange.copy(alpha = 0.4f)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "গেমার লগইন করুন",
                        color = Color.White,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(20.dp))

                    OutlinedTextField(
                        value = email,
                        onValueChange = { email = it },
                        label = { Text("ইমেইল অ্যাড্রেস", color = FF_Text_Secondary) },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = FF_Orange,
                            unfocusedBorderColor = Color.Gray.copy(alpha = 0.5f),
                            focusedLabelColor = FF_Orange,
                            unfocusedLabelColor = FF_Text_Secondary
                        ),
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    OutlinedTextField(
                        value = password,
                        onValueChange = { password = it },
                        label = { Text("গোপন পাসওয়ার্ড", color = FF_Text_Secondary) },
                        visualTransformation = PasswordVisualTransformation(),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = FF_Orange,
                            unfocusedBorderColor = Color.Gray.copy(alpha = 0.5f),
                            focusedLabelColor = FF_Orange,
                            unfocusedLabelColor = FF_Text_Secondary
                        ),
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    Button(
                        onClick = { viewModel.login(email, password) },
                        colors = ButtonDefaults.buttonColors(containerColor = FF_Orange),
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp)
                    ) {
                        Text(
                            text = "লগইন করুন",
                            color = Color.White,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    TextButton(onClick = { viewModel.currentScreen = Screen.SignUp }) {
                        Text(
                            text = "নতুন অ্যাকাউন্ট তৈরি করতে এখানে ক্লিক করুন ➔",
                            color = FF_Yellow,
                            fontSize = 14.sp
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))
                    Box(modifier = Modifier.fillMaxWidth().height(1.dp).background(Color.Gray.copy(alpha = 0.2f)))
                    Spacer(modifier = Modifier.height(12.dp))

                    Text("⚡ এক ক্লিকে সহজ এডমিন লগইন:", color = Color.LightGray, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Button(
                            onClick = { 
                                email = "bd1rakib6677@gmail.com"
                                password = "rakib123"
                                viewModel.login("bd1rakib6677@gmail.com", "rakib123")
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = FF_Orange_Light),
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier.weight(1.3f).height(38.dp),
                            contentPadding = PaddingValues(horizontal = 4.dp, vertical = 2.dp)
                        ) {
                            Text("রাকিব এডমিন 👑", color = Color.White, fontSize = 11.sp, fontWeight = FontWeight.Black)
                        }
                        Button(
                            onClick = { 
                                email = "bd1admin@gmail.com"
                                password = "BOOYAH_admin_2026"
                                viewModel.login("bd1admin@gmail.com", "BOOYAH_admin_2026")
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = FF_Card_Bg_Tinted),
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier.weight(1.3f).height(38.dp).border(1.dp, FF_Yellow.copy(alpha = 0.5f), RoundedCornerShape(8.dp)),
                            contentPadding = PaddingValues(horizontal = 4.dp, vertical = 2.dp)
                        ) {
                            Text("শান্ত এডমিন 🔧", color = FF_Yellow, fontSize = 11.sp, fontWeight = FontWeight.Black)
                        }
                    }
                }
            }

            // Safety Information & Guidelines
            Spacer(modifier = Modifier.height(24.dp))
            Surface(
                color = Color.Black.copy(alpha = 0.5f),
                border = BorderStroke(1.dp, FF_Orange.copy(alpha = 0.2f)),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text("🔥", fontSize = 16.sp)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            "BOOYAH ARENA RULES & SAFETY",
                            color = FF_Orange,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 1.sp
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        "• অবশ্যই আপনার রিয়েল ফ্রি ফায়ার UID এবং IGN দিয়ে টুর্নামেন্ট গুলোতে জয়েন করুন।\n" +
                        "• পেমেন্ট রিকোয়েস্ট বা উইথড্র করার সময় বিকাশ, নগদ অথবা রকেট রিসিভার সঠিক নাম্বারটি চেক করুন।\n" +
                        "• যেকোনো রুম আইডি ও পাসওয়ার্ড ম্যাচ শুরুর নিয়মিত ১৫ মিনিট পূর্বে আপনার নিবন্ধিত ম্যাচ সেকশনে এবং পুশ নোটিফিকেশনে দেখানো হবে।",
                        color = FF_Text_Secondary,
                        fontSize = 11.sp,
                        lineHeight = 16.sp
                    )
                }
            }
        }
    }
}

// ==========================================
// 2. SIGN UP SCREEN
// ==========================================
@Composable
fun SignUpScreen(viewModel: MainViewModel) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var fullName by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var ffUid by remember { mutableStateOf("") }
    var ffIgn by remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(FF_Dark_Bg, Color(0xFF1E1430), FF_Dark_Bg)
                )
            )
            .padding(24.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
        ) {
            GameLogoHeader()

            Spacer(modifier = Modifier.height(16.dp))

            Surface(
                color = FF_Card_Bg,
                shape = RoundedCornerShape(16.dp),
                border = BorderStroke(1.dp, FF_Orange.copy(alpha = 0.4f)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "নতুন গেমার রেজিস্ট্রেশন",
                        color = Color.White,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(16.dp))

                    OutlinedTextField(
                        value = fullName,
                        onValueChange = { fullName = it },
                        label = { Text("পূর্ণ নাম (Full Name)", color = FF_Text_Secondary) },
                        colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = FF_Orange, unfocusedBorderColor = Color.Gray.copy(alpha = 0.5f)),
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    OutlinedTextField(
                        value = email,
                        onValueChange = { email = it },
                        label = { Text("ইমেইল অ্যাড্রেস", color = FF_Text_Secondary) },
                        colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = FF_Orange, unfocusedBorderColor = Color.Gray.copy(alpha = 0.5f)),
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    OutlinedTextField(
                        value = phone,
                        onValueChange = { phone = it },
                        label = { Text("মোবাইল নাম্বার (bKash/Nagad)", color = FF_Text_Secondary) },
                        colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = FF_Orange, unfocusedBorderColor = Color.Gray.copy(alpha = 0.5f)),
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Row(modifier = Modifier.fillMaxWidth()) {
                        OutlinedTextField(
                            value = ffUid,
                            onValueChange = { ffUid = it },
                            label = { Text("FF Uid", color = FF_Text_Secondary) },
                            colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = FF_Orange, unfocusedBorderColor = Color.Gray.copy(alpha = 0.5f)),
                            modifier = Modifier
                                .weight(1f)
                                .padding(end = 6.dp),
                            singleLine = true
                        )
                        OutlinedTextField(
                            value = ffIgn,
                            onValueChange = { ffIgn = it },
                            label = { Text("FF IGN (গেম নাম)", color = FF_Text_Secondary) },
                            colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = FF_Orange, unfocusedBorderColor = Color.Gray.copy(alpha = 0.5f)),
                            modifier = Modifier
                                .weight(1f)
                                .padding(start = 6.dp),
                            singleLine = true
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    OutlinedTextField(
                        value = password,
                        onValueChange = { password = it },
                        label = { Text("পাসওয়ার্ড নিশ্চিত করুন", color = FF_Text_Secondary) },
                        visualTransformation = PasswordVisualTransformation(),
                        colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = FF_Orange, unfocusedBorderColor = Color.Gray.copy(alpha = 0.5f)),
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    Button(
                        onClick = {
                            viewModel.register(email, password, fullName, phone, ffUid, ffIgn)
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = FF_Orange),
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp)
                    ) {
                        Text(
                            text = "রেজিস্ট্রেশন করুন",
                            color = Color.White,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    TextButton(onClick = { viewModel.currentScreen = Screen.Login }) {
                        Text(
                            text = "ইতিমধ্যে অ্যাকাউন্ট আছে? প্রবেশ করুন",
                            color = FF_Yellow,
                            fontSize = 14.sp
                        )
                    }
                }
            }
        }
    }
}

// ==========================================
// 3. HOME / TOURNAMENTS LIST SCREEN
// ==========================================
@Composable
fun HomeScreen(viewModel: MainViewModel) {
    val tournaments by viewModel.tournaments.collectAsState()
    val registrations by viewModel.userRegistrations.collectAsState()
    val activeUser = viewModel.currentUser

    var filterTag by remember { mutableStateOf("ALL") } // ALL, SOLO, DUO, SQUAD, ACTIVE
    var showJoinDialog by remember { mutableStateOf<TournamentEntity?>(null) }

    Column(modifier = Modifier.fillMaxSize()) {
        // Welcome and Banner Header
        HomeHeaderSection(activeUser, viewModel)

        // Filters UI Row
        TournamentFilterRow(
            currentFilter = filterTag,
            onFilterChanged = { filterTag = it }
        )

        val filteredTournaments = remember(tournaments, filterTag, registrations) {
            tournaments.filter { t ->
                val matchesFilter = when (filterTag) {
                    "ALL" -> true
                    "SOLO" -> t.gameMode.uppercase() == "SOLO"
                    "DUO" -> t.gameMode.uppercase() == "DUO"
                    "SQUAD" -> t.gameMode.uppercase() == "SQUAD"
                    "MY" -> registrations.any { r -> r.tournamentId == t.id }
                    else -> true
                }
                matchesFilter
            }
        }

        if (filterTag == "PROFILE") {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                GamerProfileSection(activeUser, registrations, viewModel)
            }
        } else if (filteredTournaments.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("কোন টুর্নামেন্ট পাওয়া যায়নি!", color = FF_Text_Secondary, fontSize = 16.sp)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("নতুন টুর্নামেন্ট দেখতে কিছুক্ষণ পর আবার চেষ্টা করুন।", color = Color.Gray, fontSize = 13.sp)
                }
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(filteredTournaments, key = { it.id }) { tournament ->
                    val isJoined = registrations.any { it.tournamentId == tournament.id }
                    TournamentCard(
                        tournament = tournament,
                        isJoined = isJoined,
                        onJoinClick = { showJoinDialog = tournament }
                    )
                }
            }
        }
    }

    // Modal dialogue for joining tournament
    if (showJoinDialog != null) {
        val selectedTournament = showJoinDialog!!
        JoinTournamentDialog(
            tournament = selectedTournament,
            user = activeUser,
            onDismiss = { showJoinDialog = null },
            onConfirmJoin = { uid, ign ->
                viewModel.joinTournament(selectedTournament, uid, ign) { success ->
                    if (success) {
                        showJoinDialog = null
                    }
                }
            }
        )
    }
}

// ==========================================
// 4. MY MATCHES SCREEN (ROOM CODES DETECTOR)
// ==========================================
@Composable
fun MyMatchesScreen(viewModel: MainViewModel) {
    val registrations by viewModel.userRegistrations.collectAsState()
    val tournaments by viewModel.tournaments.collectAsState()

    val myBookedTournaments = remember(registrations, tournaments) {
        tournaments.filter { t ->
            registrations.any { it.tournamentId == t.id }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "আমার টুর্নামেন্ট সমূহ",
            color = Color.White,
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = "যেখানে আপনি জয়লাভ করে প্রাইজ ও আর্নিং অর্জন করবেন!",
            color = FF_Text_Secondary,
            fontSize = 12.sp,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        if (myBookedTournaments.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("আপনি এখনও কোনো টুর্নামেন্টে যোগ দেননি!", color = FF_Text_Secondary, fontSize = 16.sp)
                    Spacer(modifier = Modifier.height(12.dp))
                    Button(
                        onClick = { viewModel.currentScreen = Screen.Home },
                        colors = ButtonDefaults.buttonColors(containerColor = FF_Orange)
                    ) {
                        Text("টুর্নামেন্ট দেখুন", color = Color.White)
                    }
                }
            }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(myBookedTournaments, key = { it.id }) { tournament ->
                    val userReg = registrations.firstOrNull { it.tournamentId == tournament.id }
                    MyMatchCardItem(tournament, userReg)
                }
            }
        }
    }
}

@Composable
fun MyMatchCardItem(tournament: TournamentEntity, registration: RegistrationEntity?) {
    val clipboardManager = LocalClipboardManager.current

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = FF_Card_Bg),
        border = BorderStroke(1.dp, if (tournament.status == "ONGOING") FF_Yellow.copy(alpha = 0.8f) else Color.Gray.copy(alpha = 0.2f))
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // Header: Title and Status Tag
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = tournament.title,
                        color = Color.White,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "${tournament.date} | ${tournament.time}",
                        color = FF_Text_Secondary,
                        fontSize = 12.sp
                    )
                }

                // Match Status Tag
                val (color, label) = when (tournament.status) {
                    "UPCOMING" -> Pair(FF_Orange, "UPCOMING")
                    "ONGOING" -> Pair(FF_Yellow, "LIVE/RELEASING")
                    else -> Pair(FF_Green, "FINISHED")
                }
                Surface(
                    color = color.copy(alpha = 0.2f),
                    shape = RoundedCornerShape(4.dp),
                    border = BorderStroke(1.dp, color)
                ) {
                    Text(
                        text = label,
                        color = color,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }
            }

            Divider(color = Color.Gray.copy(alpha = 0.2f), modifier = Modifier.padding(vertical = 12.dp))

            // Map and Mode info
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Column {
                    Text("ম্যাপ", color = FF_Text_Secondary, fontSize = 11.sp)
                    Text(tournament.map, color = Color.White, fontSize = 14.sp, fontWeight = FontWeight.Bold)
                }
                Column {
                    Text("গেম মোড", color = FF_Text_Secondary, fontSize = 11.sp)
                    Text(tournament.gameMode, color = Color.White, fontSize = 14.sp, fontWeight = FontWeight.Bold)
                }
                Column(horizontalAlignment = Alignment.End) {
                    Text("আমার FF IGN", color = FF_Text_Secondary, fontSize = 11.sp)
                    Text(registration?.ffIgn ?: "None", color = FF_Yellow, fontSize = 14.sp, fontWeight = FontWeight.Bold)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // AUTO ROOM CREDENTIALS CARD
            if (tournament.status == "ONGOING" || tournament.status == "UPCOMING" && tournament.roomId.isNotEmpty()) {
                Surface(
                    color = Color.Black.copy(alpha = 0.6f),
                    shape = RoundedCornerShape(8.dp),
                    border = BorderStroke(1.dp, FF_Yellow.copy(alpha = 0.5f)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text("🔑 রুমের তথ্য উন্মোচন হয়েছে! (অটমেটেড)", color = FF_Yellow, fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
                        }
                        Spacer(modifier = Modifier.height(8.dp))

                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                            Column {
                                Text("রুম আইডি (Room ID)", color = Color.LightGray, fontSize = 11.sp)
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Text(tournament.roomId, color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text(
                                        text = "[কপি]",
                                        color = FF_Orange,
                                        fontSize = 11.sp,
                                        modifier = Modifier
                                            .clickable { clipboardManager.setText(AnnotatedString(tournament.roomId)) }
                                            .padding(4.dp)
                                    )
                                }
                            }

                            Column(horizontalAlignment = Alignment.End) {
                                Text("পাসওয়ার্ড (Password)", color = Color.LightGray, fontSize = 11.sp)
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Text(tournament.roomPassword, color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text(
                                        text = "[কপি]",
                                        color = FF_Orange,
                                        fontSize = 11.sp,
                                        modifier = Modifier
                                            .clickable { clipboardManager.setText(AnnotatedString(tournament.roomPassword)) }
                                            .padding(4.dp)
                                    )
                                }
                            }
                        }
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = "*রুম শুরু হওয়ার ৫ মিনিট পূর্বে গেমে প্রবেশ করুন। হ্যাক বা অনৈতিক কিছু ব্যবহার করলে ব্যান করা হবে।*",
                            color = FF_Red,
                            fontSize = 9.sp,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
            } else if (tournament.status == "UPCOMING") {
                // Not yet released room details
                Surface(
                    color = Color.Gray.copy(alpha = 0.1f),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "⏳ রুম আইডি ও পাসওয়ার্ড খেলা শুরুর ১৫ মিনিট পূর্বে এখানে দেওয়া হবে।",
                            color = FF_Text_Secondary,
                            fontSize = 11.sp,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            } else {
                // Completed results summary
                Surface(
                    color = FF_Green.copy(alpha = 0.1f),
                    shape = RoundedCornerShape(8.dp),
                    border = BorderStroke(1.dp, FF_Green.copy(alpha = 0.4f)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Text("📊 খেলার চূড়ান্ত ফলাফল (Result)", color = FF_Green, fontSize = 13.sp, fontWeight = FontWeight.Bold)
                        Spacer(modifier = Modifier.height(6.dp))
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Text("র‍্যাংক: #${registration?.rank ?: "N/A"}", color = Color.White, fontSize = 13.sp)
                            Text("মোট কিলস: ${registration?.kills ?: 0}", color = Color.White, fontSize = 13.sp)
                            Text("টোটাল উইনিং: ${registration?.rewardEarned ?: 0.0}৳", color = FF_Yellow, fontSize = 13.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }
    }
}

// ==========================================
// 5. WALLET / DEPOSIT & WITHDRAW (bKash, Nagad, Rocket)
// ==========================================
@Composable
fun WalletScreen(viewModel: MainViewModel) {
    val activeUser = viewModel.currentUser ?: return
    val paymentsHistory by viewModel.userPayments.collectAsState()

    var activeTab by remember { mutableStateOf("DEPOSIT") } // DEPOSIT, WITHDRAW, HISTORY
    var walletMethod by remember { mutableStateOf("bKash") } // bKash, Nagad, Rocket
    var amountText by remember { mutableStateOf("") }
    var senderOrReceiverPhone by remember { mutableStateOf("") }
    var transactionIdText by remember { mutableStateOf("") }

    val clipboardManager = LocalClipboardManager.current

    // Admin Numbers (For Demo Send Money reference)
    val adminNumber = when (walletMethod) {
        "bKash" -> "01783928172"
        "Nagad" -> "01948372648"
        else -> "01574839284"
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        // WALLET BALANCE HEADER
        Surface(
            color = FF_Card_Bg,
            shape = RoundedCornerShape(16.dp),
            border = BorderStroke(1.dp, FF_Yellow.copy(alpha = 0.3f)),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(20.dp)) {
                Text("আমার মূল ব্যালেন্স (Available Balance)", color = FF_Text_Secondary, fontSize = 12.sp)
                Text("${activeUser.balance}৳", color = FF_Yellow, fontSize = 36.sp, fontWeight = FontWeight.Black)

                Spacer(modifier = Modifier.height(8.dp))
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text("ইউজার আইডি: ${activeUser.ffUid}", color = Color.LightGray, fontSize = 11.sp)
                    Text("ffIGN: ${activeUser.ffIgn}", color = Color.LightGray, fontSize = 11.sp)
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // TAB SWITCHES
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(8.dp))
                .background(Color.White.copy(alpha = 0.05f))
        ) {
            listOf("DEPOSIT" to "টাকা ডিপোজিট", "WITHDRAW" to "টাকা উত্তোলন", "HISTORY" to "হিস্টরি").forEach { (tabKey, title) ->
                val isSelected = activeTab == tabKey
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .clickable { activeTab = tabKey }
                        .background(if (isSelected) FF_Orange else Color.Transparent)
                        .padding(vertical = 12.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = title,
                        color = if (isSelected) Color.White else FF_Text_Secondary,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        if (activeTab == "HISTORY") {
            // PAYMENT HISTORY LIST
            Text("টাকা ডিপোজিট ও উত্তোলনের বিবরণ", color = Color.LightGray, fontSize = 14.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(12.dp))

            if (paymentsHistory.isEmpty()) {
                Text(
                    "এখনো পর্যন্ত কোনো ট্রানজেকশন হয়নি!",
                    color = Color.Gray,
                    fontSize = 13.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth().padding(32.dp)
                )
            } else {
                paymentsHistory.forEach { payment ->
                    PaymentHistoryItem(payment)
                    Spacer(modifier = Modifier.height(10.dp))
                }
            }
        } else {
            // DEPOSIT AND WITHDRAW FORMS
            Text(if (activeTab == "DEPOSIT") "মোবাইল ব্যাংকিং ডিপোজিট ফর্ম" else "ব্যালেন্স বা রিওয়ার্ড উত্তোলন ফর্ম", color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(12.dp))

            // METHOD CHOOSE (bKash / Nagad / Rocket)
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                listOf("bKash" to BKash_Pink, "Nagad" to Nagad_Orange, "Rocket" to Rocket_Purple).forEach { (m, color) ->
                    val isSelected = walletMethod == m
                    Surface(
                        color = if (isSelected) color else color.copy(alpha = 0.15f),
                        border = BorderStroke(1.dp, if (isSelected) Color.White else color),
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier
                            .weight(1f)
                            .clickable { walletMethod = m }
                    ) {
                        Text(
                            text = m,
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp,
                            modifier = Modifier
                                .padding(vertical = 10.dp),
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            if (activeTab == "DEPOSIT") {
                // VISUAL STEP TRACKER WIZARD
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    listOf("১. ক্যাশ ইন/সেন্ডমানি", "২. ট্রানজেকশন তথ্য", "৩. অটো ভেরিফিকেশন").forEachIndexed { index, step ->
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .background(FF_Card_Bg_Tinted, RoundedCornerShape(4.dp))
                                .border(1.dp, if (index == 1) FF_Orange else Color.Transparent, RoundedCornerShape(4.dp))
                                .padding(vertical = 6.dp, horizontal = 4.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(step, color = if (index == 1) FF_Yellow else Color.White.copy(alpha = 0.5f), fontSize = 9.sp, fontWeight = FontWeight.Bold, maxLines = 1)
                        }
                    }
                }

                // Deposit Reference / Instructions
                Surface(
                    color = walletMethodColor(walletMethod).copy(alpha = 0.1f),
                    border = BorderStroke(1.dp, walletMethodColor(walletMethod).copy(alpha = 0.3f)),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text("⚡", fontSize = 14.sp)
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("নিচের ${walletMethod} পার্সোনাল নাম্বারে 'Send Money' করুন:", color = Color.White, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                        }
                        Spacer(modifier = Modifier.height(10.dp))
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                            Text(adminNumber, color = FF_Yellow, fontSize = 20.sp, fontWeight = FontWeight.Black, letterSpacing = 1.sp)
                            Button(
                                onClick = { clipboardManager.setText(AnnotatedString(adminNumber)) },
                                colors = ButtonDefaults.buttonColors(containerColor = walletMethodColor(walletMethod)),
                                contentPadding = PaddingValues(horizontal = 14.dp, vertical = 6.dp),
                                shape = RoundedCornerShape(8.dp),
                                modifier = Modifier.height(34.dp)
                            ) {
                                Text("কপি করুন", color = Color.White, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                            }
                        }
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(
                            "টাকা সঠিকভাবে সেন্ড মানি করার পর নিচের ফরমে সঠিক পরিমাণ, আপনার মোবাইল নাম্বার এবং ট্রানজেকশন ট্র্যাকিং আইডি (TrxID) দিন। এডমিন ভেরিফাই করে ১ থেকে ১০ মিনিটের মধ্যে ব্যালেন্স যোগ করবেন।",
                            color = FF_Text_Secondary,
                            fontSize = 10.sp,
                            lineHeight = 15.sp
                        )
                    }
                }

                Text("পরিমাণ পছন্দ করুন (Presets)", color = Color.LightGray, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(6.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    listOf("50", "100", "200", "500", "1000").forEach { preset ->
                        Surface(
                            color = if (amountText == preset) walletMethodColor(walletMethod) else FF_Card_Bg,
                            border = BorderStroke(1.dp, if (amountText == preset) Color.White else Color.Gray.copy(alpha = 0.3f)),
                            shape = RoundedCornerShape(6.dp),
                            modifier = Modifier
                                .weight(1f)
                                .clickable { amountText = preset }
                        ) {
                            Text(
                                text = "+${preset}৳",
                                color = Color.White,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(vertical = 8.dp),
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = amountText,
                    onValueChange = { amountText = it },
                    label = { Text("কত টাকা পাঠিয়েছেন (BDT)", color = FF_Text_Secondary) },
                    colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = FF_Orange),
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(12.dp))

                OutlinedTextField(
                    value = senderOrReceiverPhone,
                    onValueChange = { senderOrReceiverPhone = it },
                    label = { Text("আপনার মোবাইল নাম্বার (১১ সংখ্যা)", color = FF_Text_Secondary) },
                    colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = FF_Orange),
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(12.dp))

                OutlinedTextField(
                    value = transactionIdText,
                    onValueChange = { transactionIdText = it },
                    label = { Text("ট্রানজেকশন আইডি (TrxID eg: BK847J9D6T)", color = FF_Text_Secondary) },
                    colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = FF_Orange),
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(20.dp))

                Button(
                    onClick = {
                        val amt = amountText.toDoubleOrNull() ?: 0.0
                        if (amt < 50.0) {
                            viewModel.errorMessage = "নূন্যতম ডিপোজিট ৫০ টাকা!"
                            return@Button
                        }
                        if (senderOrReceiverPhone.length < 11) {
                            viewModel.errorMessage = "দয়া করে সঠিক ১১ সংখ্যার মোবাইল নাম্বার দিন!"
                            return@Button
                        }
                        if (transactionIdText.trim().isEmpty()) {
                            viewModel.errorMessage = "দয়া করে ট্রানজেকশন আইডি প্রদান করুন!"
                            return@Button
                        }
                        viewModel.submitDeposit(walletMethod, amt, senderOrReceiverPhone, transactionIdText)
                        // Clear input
                        amountText = ""
                        senderOrReceiverPhone = ""
                        transactionIdText = ""
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = walletMethodColor(walletMethod)),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                ) {
                    Text("নিরাপদ পেমেন্ট ভেরিফাই করুন 🛡️", color = Color.White, fontWeight = FontWeight.Bold)
                }

            } else {
                // Withdraw configuration
                Surface(
                    color = FF_Orange_Light.copy(alpha = 0.2f),
                    border = BorderStroke(1.dp, FF_Orange.copy(alpha = 0.2f)),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Text("🛡️ ব্যালেন্স উত্তোলন নীতিমালা:", color = Color.White, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            "• টুর্নামেন্ট জিতে বা ব্যালেন্স ডিপোজিট রিফান্ড থেকে আপনার ক্যাশআউট সুবিধা পাবেন।\n" +
                            "• নূন্যতম ক্যাশআউট লিমিট ১০০ টাকা। সর্বোচ্চ ক্যাশআউট লিমিট ২৫,০০০ টাকা প্রতিদিন।\n" +
                            "• সাধারণত পেমেন্ট রিকোয়েস্ট করার ১ থেকে ২৪ ঘন্টার মধ্যে তা সরাসরি আপনার প্রদেয় মোবাইল ব্যাংকিং নাম্বারে পৌঁছে যাবে।",
                            color = FF_Text_Secondary,
                            fontSize = 11.sp,
                            lineHeight = 16.sp
                        )
                    }
                }

                Text("উত্তোলনের পরিমাণ পছন্দ করুন", color = Color.LightGray, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(6.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    listOf("100", "200", "500", "1000", "2000").forEach { preset ->
                        Surface(
                            color = if (amountText == preset) walletMethodColor(walletMethod) else FF_Card_Bg,
                            border = BorderStroke(1.dp, if (amountText == preset) Color.White else Color.Gray.copy(alpha = 0.3f)),
                            shape = RoundedCornerShape(6.dp),
                            modifier = Modifier
                                .weight(1f)
                                .clickable { amountText = preset }
                        ) {
                            Text(
                                text = "${preset}৳",
                                color = Color.White,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(vertical = 8.dp),
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = amountText,
                    onValueChange = { amountText = it },
                    label = { Text("টাকার পরিমাণ (নূন্যতম ১০০৳)", color = FF_Text_Secondary) },
                    colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = FF_Orange),
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(12.dp))

                OutlinedTextField(
                    value = senderOrReceiverPhone,
                    onValueChange = { senderOrReceiverPhone = it },
                    label = { Text("যে নাম্বারে ক্যাশআউট নিবেন (${walletMethod})", color = FF_Text_Secondary) },
                    colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = FF_Orange),
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(24.dp))

                Button(
                    onClick = {
                        val amt = amountText.toDoubleOrNull() ?: 0.0
                        if (amt < 100.0) {
                            viewModel.errorMessage = "নূন্যতম উত্তোলন ১০০ টাকা হতে হবে!"
                            return@Button
                        }
                        if (amt > activeUser.balance) {
                            viewModel.errorMessage = "আপনার একাউন্টে পর্যাপ্ত উইনিং ব্যালেন্স নেই!"
                            return@Button
                        }
                        if (senderOrReceiverPhone.length < 11) {
                            viewModel.errorMessage = "সঠিক ১১ সংখ্যার রিসিভার নাম্বার দিন!"
                            return@Button
                        }
                        viewModel.submitWithdraw(walletMethod, amt, senderOrReceiverPhone)
                        // Clear input
                        amountText = ""
                        senderOrReceiverPhone = ""
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = walletMethodColor(walletMethod)),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                ) {
                    Text("টাকা উত্তোলনের রিকোয়েস্ট নিশ্চিত করুন ➔", color = Color.White, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

@Composable
fun PaymentHistoryItem(payment: PaymentEntity) {
    Surface(
        color = FF_Card_Bg,
        shape = RoundedCornerShape(8.dp),
        border = BorderStroke(1.dp, Color.Gray.copy(alpha = 0.15f))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    val labelBg = if (payment.type == "DEPOSIT") FF_Green else FF_Orange
                    Text(
                        text = if (payment.type == "DEPOSIT") "ডিপোজিট" else "উত্তোলন",
                        color = Color.White,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .background(labelBg, RoundedCornerShape(4.dp))
                            .padding(horizontal = 6.dp, vertical = 2.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(payment.method, color = Color.White, fontSize = 14.sp, fontWeight = FontWeight.Bold)
                }
                Spacer(modifier = Modifier.height(4.dp))
                Text("নাম্বার: ${payment.phone}", color = Color.LightGray, fontSize = 11.sp)
                Text("ID: ${payment.transactionId}", color = Color.Gray, fontSize = 10.sp)
            }

            Column(horizontalAlignment = Alignment.End) {
                Text(
                    text = "${if (payment.type == "DEPOSIT") "+" else "-"}${payment.amount}৳",
                    color = if (payment.type == "DEPOSIT") FF_Green else FF_Yellow,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )

                val (badgeColor, txt) = when (payment.status) {
                    "PENDING" -> Pair(FF_Yellow, "অপেক্ষমান")
                    "APPROVED" -> Pair(FF_Green, "সফল")
                    else -> Pair(FF_Red, "বাতিল")
                }
                Text(txt, color = badgeColor, fontSize = 11.sp, fontWeight = FontWeight.Medium)
            }
        }
    }
}

// ==========================================
// 6. AD-MIN PANEL SCREEN
// ==========================================
@Composable
fun AdminPanelScreen(viewModel: MainViewModel) {
    val loggedUser = viewModel.currentUser
    if (loggedUser == null || !loggedUser.isAdmin) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(FF_Dark_Bg),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text("⚠️ অননুমোদিত অ্যাক্সেস ব্লক করা হয়েছে!", color = FF_Red, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(10.dp))
                Text("দয়া করে সঠিক এডমিন ক্রেডেনশিয়াল ব্যবহার করুন।", color = Color.Gray, fontSize = 13.sp)
            }
        }
        LaunchedEffect(Unit) {
            viewModel.currentScreen = Screen.Home
        }
        return
    }

    val users by viewModel.allUsers.collectAsState()
    val allPayments by viewModel.allPayments.collectAsState()
    val tournaments by viewModel.tournaments.collectAsState()

    var activeViewTab by remember { mutableStateOf("TOURNAMENTS") } // TOURNAMENTS, PAYMENTS, USERS

    // Tournament creation state
    var title by remember { mutableStateOf("") }
    var date by remember { mutableStateOf("") }
    var time by remember { mutableStateOf("") }
    var mapName by remember { mutableStateOf("Bermuda") }
    var gameMode by remember { mutableStateOf("Solo") }
    var entryFee by remember { mutableStateOf("50") }
    var prizePool by remember { mutableStateOf("2000") }
    var booyahPrize by remember { mutableStateOf("1000") }
    var perKillPrize by remember { mutableStateOf("10") }
    var maxPlayers by remember { mutableStateOf("48") }

    val totalUsers = users.size
    val totalTournaments = tournaments.size
    val totalDepositsVal = allPayments.filter { it.type == "DEPOSIT" && it.status == "APPROVED" }.sumOf { it.amount }
    val pendingWithdrawalsVal = allPayments.filter { it.type == "WITHDRAW" && it.status == "PENDING" }.sumOf { it.amount }

    var searchQuery by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Text("🔧 এডমিন প্যানেল (Admin Master Control)", color = FF_Orange, fontSize = 18.sp, fontWeight = FontWeight.Bold)
        Text("অটোমেটেড টুর্নামেন্ট ও পেমেন্ট রুল কন্ট্রোল করুন", color = Color.LightGray, fontSize = 11.sp, modifier = Modifier.padding(bottom = 12.dp))

        // PREMIUM STATS ANALYTICS BANNER GRID
        Row(
            modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            val stats = listOf(
                Triple("মোট ইউজার", "$totalUsers জন", FF_Orange),
                Triple("মোট ডিপোজিট", "${totalDepositsVal}৳", FF_Green),
                Triple("উইথড্র পেন্ডিং", "${pendingWithdrawalsVal}৳", FF_Yellow),
                Triple("মোট টুর্নামেন্ট", "$totalTournaments টি", Color.Cyan)
            )
            stats.forEach { (title, value, color) ->
                Card(
                    modifier = Modifier.weight(1f),
                    colors = CardDefaults.cardColors(containerColor = FF_Card_Bg),
                    border = BorderStroke(1.dp, color.copy(alpha = 0.25f)),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(8.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(title, color = Color.Gray, fontSize = 9.sp, fontWeight = FontWeight.Bold, maxLines = 1)
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(value, color = color, fontSize = 12.sp, fontWeight = FontWeight.Black, maxLines = 1)
                    }
                }
            }
        }

        // VIEW TOGGLES WITH BEAUTIFUL ANIMATIONS
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            listOf("TOURNAMENTS" to "টুর্নামেন্ট", "PAYMENTS" to "পেমেন্ট (${allPayments.count { it.status == "PENDING" }})", "USERS" to "ইউজার গেমার").forEach { (tab, label) ->
                val isSelected = activeViewTab == tab
                val bgAnim by animateColorAsState(
                    targetValue = if (isSelected) FF_Orange_Light else FF_Card_Bg,
                    animationSpec = tween(durationMillis = 300, easing = FastOutSlowInEasing),
                    label = "adm_tab_bg"
                )
                val borderAnim by animateColorAsState(
                    targetValue = if (isSelected) FF_Orange else Color.Gray.copy(alpha = 0.2f),
                    animationSpec = tween(durationMillis = 300, easing = FastOutSlowInEasing),
                    label = "adm_tab_border"
                )

                Surface(
                    color = bgAnim,
                    shape = RoundedCornerShape(10.dp),
                    border = BorderStroke(1.dp, borderAnim),
                    modifier = Modifier
                        .weight(1f)
                        .clickable { activeViewTab = tab }
                ) {
                    Text(
                        text = label,
                        color = if (isSelected) Color.White else FF_Text_Secondary,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 10.dp)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Crossfade(targetState = activeViewTab, animationSpec = tween(450), label = "admin_view_transition") { tabState ->
            when (tabState) {
                "TOURNAMENTS" -> {
                    // NEW TOURNAMENT CREATION FORM
            Surface(
                color = FF_Card_Bg,
                shape = RoundedCornerShape(12.dp),
                border = BorderStroke(1.dp, FF_Orange.copy(alpha = 0.3f)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("⚔️ নতুন টুর্নামেন্ট তৈরি করুন", color = Color.White, fontSize = 15.sp, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(12.dp))

                    OutlinedTextField(
                        value = title,
                        onValueChange = { title = it },
                        label = { Text("টুর্নামেন্টের টাইটেল", color = FF_Text_Secondary) },
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(10.dp))

                    Row(modifier = Modifier.fillMaxWidth()) {
                        OutlinedTextField(
                            value = date,
                            onValueChange = { date = it },
                            label = { Text("তারিখ (YYYY-MM-DD)", color = FF_Text_Secondary) },
                            modifier = Modifier.weight(1.0f).padding(end = 4.dp)
                        )
                        OutlinedTextField(
                            value = time,
                            onValueChange = { time = it },
                            label = { Text("সময় (যেমন: 09:30 PM)", color = FF_Text_Secondary) },
                            modifier = Modifier.weight(1.0f).padding(start = 4.dp)
                        )
                    }
                    Spacer(modifier = Modifier.height(10.dp))

                    Row(modifier = Modifier.fillMaxWidth()) {
                        OutlinedTextField(
                            value = mapName,
                            onValueChange = { mapName = it },
                            label = { Text("ম্যাপ", color = FF_Text_Secondary) },
                            modifier = Modifier.weight(1f).padding(end = 4.dp)
                        )
                        OutlinedTextField(
                            value = gameMode,
                            onValueChange = { gameMode = it },
                            label = { Text("মোড (Solo/Duo/Squad)", color = FF_Text_Secondary) },
                            modifier = Modifier.weight(1f).padding(start = 4.dp)
                        )
                    }
                    Spacer(modifier = Modifier.height(10.dp))

                    Row(modifier = Modifier.fillMaxWidth()) {
                        OutlinedTextField(
                            value = entryFee,
                            onValueChange = { entryFee = it },
                            label = { Text("এন্ট্রি ফি (৳)", color = FF_Text_Secondary) },
                            modifier = Modifier.weight(1f).padding(end = 4.dp)
                        )
                        OutlinedTextField(
                            value = prizePool,
                            onValueChange = { prizePool = it },
                            label = { Text("প্রাইজ পুল (৳)", color = FF_Text_Secondary) },
                            modifier = Modifier.weight(1f).padding(start = 4.dp)
                        )
                    }
                    Spacer(modifier = Modifier.height(10.dp))

                    Row(modifier = Modifier.fillMaxWidth()) {
                        OutlinedTextField(
                            value = booyahPrize,
                            onValueChange = { booyahPrize = it },
                            label = { Text("Booyah বিজয়ী প্রাইজ", color = FF_Text_Secondary) },
                            modifier = Modifier.weight(1f).padding(end = 4.dp)
                        )
                        OutlinedTextField(
                            value = perKillPrize,
                            onValueChange = { perKillPrize = it },
                            label = { Text("প্রতি কিল রিওয়ার্ড (৳)", color = FF_Text_Secondary) },
                            modifier = Modifier.weight(1f).padding(start = 4.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Button(
                        onClick = {
                            viewModel.createTournament(
                                title = title,
                                date = date,
                                time = time,
                                map = mapName,
                                gameMode = gameMode,
                                entryFee = entryFee.toDoubleOrNull() ?: 0.0,
                                prizePool = prizePool.toDoubleOrNull() ?: 0.0,
                                booyahPrize = booyahPrize.toDoubleOrNull() ?: 0.0,
                                perKillPrize = perKillPrize.toDoubleOrNull() ?: 0.0,
                                roomId = "",
                                roomPassword = "",
                                maxPlayers = maxPlayers.toIntOrNull() ?: 48
                            )
                            // Clear inputs
                            title = ""
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = FF_Orange),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("টুর্নামেন্ট তৈরি করুন", color = Color.White, fontWeight = FontWeight.Bold)
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            Text("⚔️ বর্তমান সমস্ত টুর্নামেন্ট সমূহ (${tournaments.size}) - রুম কোড যোগ করুন / কমপ্লিট করুন", color = Color.White, fontSize = 14.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(10.dp))

            tournaments.forEach { tournament ->
                var showRoomEdit by remember { mutableStateOf(false) }
                var roomIdVal by remember { mutableStateOf(tournament.roomId) }
                var roomPassVal by remember { mutableStateOf(tournament.roomPassword) }

                Card(
                    modifier = Modifier.fillMaxWidth().padding(bottom = 12.dp),
                    colors = CardDefaults.cardColors(containerColor = FF_Card_Bg),
                    border = BorderStroke(1.dp, Color.Gray.copy(alpha = 0.2f))
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Column {
                                Text(tournament.title, color = Color.White, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                                Text("Map: ${tournament.map} | Status: ${tournament.status}", color = FF_Text_Secondary, fontSize = 11.sp)
                                Text("রুম আইডি: ${tournament.roomId.ifEmpty { "প্রদান করা হয়নি" }}", color = FF_Yellow, fontSize = 12.sp)
                            }

                            IconButton(onClick = { viewModel.deleteTournament(tournament.id) }) {
                                Text("❌", fontSize = 11.sp)
                            }
                        }

                        if (tournament.status != "COMPLETED") {
                            Row(modifier = Modifier.fillMaxWidth().padding(top = 8.dp), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                Button(
                                    onClick = { showRoomEdit = !showRoomEdit },
                                    colors = ButtonDefaults.buttonColors(containerColor = Color.White.copy(alpha = 0.1f)),
                                    modifier = Modifier.weight(1f)
                                ) {
                                    Text("🔑 রুম আইডি পাসওয়ার্ড", fontSize = 11.sp)
                                }
                                Button(
                                    onClick = {
                                        // Demo mock completion of tournament with rakib winning
                                        val mockScores = listOf(
                                            Pair("bd1rakib6677@gmail.com", Pair(1, 10)) // Rakib gets rank 1 with 10 kills
                                        )
                                        viewModel.submitTournamentStandings(tournament.id, mockScores)
                                    },
                                    colors = ButtonDefaults.buttonColors(containerColor = FF_Green),
                                    modifier = Modifier.weight(1f)
                                ) {
                                    Text("🏆 ড্র করুন ফলাফল", fontSize = 11.sp)
                                }
                            }
                        }

                        if (showRoomEdit) {
                            Column(modifier = Modifier.padding(top = 8.dp)) {
                                Row {
                                    OutlinedTextField(
                                        value = roomIdVal,
                                        onValueChange = { roomIdVal = it },
                                        label = { Text("রুম আইডি") },
                                        modifier = Modifier.weight(1f).padding(end = 4.dp)
                                    )
                                    OutlinedTextField(
                                        value = roomPassVal,
                                        onValueChange = { roomPassVal = it },
                                        label = { Text("পাসওয়ার্ড") },
                                        modifier = Modifier.weight(1f).padding(start = 4.dp)
                                    )
                                }
                                Spacer(modifier = Modifier.height(4.dp))
                                Button(
                                    onClick = {
                                        viewModel.updateRoomDetails(tournament.id, roomIdVal, roomPassVal)
                                        showRoomEdit = false
                                    },
                                    colors = ButtonDefaults.buttonColors(containerColor = FF_Yellow),
                                    modifier = Modifier.align(Alignment.End)
                                ) {
                                    Text("সংরক্ষণ করুন", color = Color.Black, fontSize = 11.sp)
                                }
                            }
                        }
                    }
                }
            }

                }
                "PAYMENTS" -> {
                    // DEPOSIT & WITHDRAW VERIFICATION QUEUE
            Text("💳 পেমেন্ট রিকুয়েস্ট পেন্ডিং তালিকা (${allPayments.count { it.status == "PENDING" }})", color = Color.White, fontSize = 14.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(10.dp))

            val pendingList = allPayments.filter { it.status == "PENDING" }
            if (pendingList.isEmpty()) {
                Text("কোন পেন্ডিং পেমেন্ট রিকুয়েস্ট নেই!", color = Color.Gray, fontSize = 13.sp, textAlign = TextAlign.Center, modifier = Modifier.fillMaxWidth().padding(32.dp))
            } else {
                pendingList.forEach { payment ->
                    Card(
                        modifier = Modifier.fillMaxWidth().padding(bottom = 12.dp),
                        colors = CardDefaults.cardColors(containerColor = FF_Card_Bg),
                        border = BorderStroke(1.dp, FF_Orange.copy(alpha = 0.3f))
                    ) {
                        Column(modifier = Modifier.padding(14.dp)) {
                            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                Column {
                                    Text(
                                        text = if (payment.type == "DEPOSIT") "📥 ডিপোজিট রিকোয়েস্ট" else "📤 ক্যাশআউট রিকোয়েস্ট",
                                        color = if (payment.type == "DEPOSIT") FF_Green else FF_Yellow,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 14.sp
                                    )
                                    Text("ইউজার ইমেইল: ${payment.userEmail}", color = Color.White, fontSize = 11.sp)
                                    Text("মেথড: ${payment.method} | নাম্বার: ${payment.phone}", color = Color.LightGray, fontSize = 12.sp)
                                    if (payment.type == "DEPOSIT") {
                                        Text("বিবরণ TrxID: ${payment.transactionId}", color = FF_Yellow, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                                    }
                                }
                                Text("${payment.amount}৳", color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.Black)
                            }

                            Spacer(modifier = Modifier.height(12.dp))

                            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                Button(
                                    onClick = { viewModel.approvePayment(payment) },
                                    colors = ButtonDefaults.buttonColors(containerColor = FF_Green),
                                    modifier = Modifier.weight(1f)
                                ) {
                                    Text("অনুমোদন দিন (Approve)", fontSize = 11.sp)
                                }
                                Button(
                                    onClick = { viewModel.rejectPayment(payment) },
                                    colors = ButtonDefaults.buttonColors(containerColor = FF_Red),
                                    modifier = Modifier.weight(1f)
                                ) {
                                    Text("বাতিল করুন (Reject)", fontSize = 11.sp)
                                }
                            }
                        }
                    }
                }
            }

            // Also show history of processed payments for feedback
            Spacer(modifier = Modifier.height(20.dp))
            Text("📜 সম্পন্ন করা পেমেন্ট ইতিহাস", color = Color.LightGray, fontSize = 13.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(10.dp))
            allPayments.filter { it.status != "PENDING" }.take(10).forEach { payment ->
                PaymentHistoryItem(payment)
                Spacer(modifier = Modifier.height(6.dp))
            }

                }
                else -> {
                    // LIVE USER SEARCH BAR
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                label = { Text("প্লেয়ার খুঁজুন (নাম / ইমেইল / UID / IGN)", color = FF_Text_Secondary) },
                colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = FF_Orange),
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                leadingIcon = { Text("🔍", fontSize = 14.sp, modifier = Modifier.padding(start = 10.dp)) }
            )
            Spacer(modifier = Modifier.height(14.dp))

            val filteredUsers = users.filter { u ->
                val q = searchQuery.trim().lowercase()
                q.isEmpty() ||
                u.fullName.lowercase().contains(q) ||
                u.email.lowercase().contains(q) ||
                u.ffUid.contains(q) ||
                u.ffIgn.lowercase().contains(q)
            }

            Text("👥 নিবন্ধিত গেমার প্লেয়ার তালিকা (${filteredUsers.size})", color = Color.White, fontSize = 14.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(10.dp))

            filteredUsers.forEach { user ->
                var adjustAmountText by remember(user.email) { mutableStateOf("") }
                Card(
                    modifier = Modifier.fillMaxWidth().padding(bottom = 12.dp),
                    colors = CardDefaults.cardColors(containerColor = FF_Card_Bg),
                    border = BorderStroke(1.dp, Color.Gray.copy(alpha = 0.2f))
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                            Column {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Text(user.fullName, color = Color.White, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                                    if (user.isAdmin) {
                                        Text(" (ADMIN)", color = FF_Orange, fontSize = 10.sp, fontWeight = FontWeight.Black, modifier = Modifier.padding(start = 6.dp))
                                    }
                                }
                                Text("ইমেইল: ${user.email}", color = Color.LightGray, fontSize = 11.sp)
                                Text("পাসওয়ার্ড: ${user.passwordHash}", color = Color.LightGray, fontSize = 10.sp)
                                Text("FF IGN: ${user.ffIgn} | UID: ${user.ffUid}", color = FF_Yellow, fontSize = 11.sp)
                            }
                            Text("${user.balance}৳", color = FF_Yellow, fontSize = 20.sp, fontWeight = FontWeight.Black)
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        // Custom amount balance adjustment controls
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            OutlinedTextField(
                                value = adjustAmountText,
                                onValueChange = { adjustAmountText = it },
                                label = { Text("টাকার পরিমাণ (সমন্বয়)", fontSize = 11.sp, color = FF_Text_Secondary) },
                                colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = FF_Orange),
                                modifier = Modifier.weight(1.2f).height(50.dp),
                                singleLine = true
                            )
                            Button(
                                onClick = {
                                    val amt = adjustAmountText.toDoubleOrNull() ?: 100.0
                                    viewModel.addTestFundsToUser(user.email, amt)
                                    adjustAmountText = ""
                                },
                                colors = ButtonDefaults.buttonColors(containerColor = FF_Green),
                                shape = RoundedCornerShape(8.dp),
                                modifier = Modifier.height(34.dp).weight(1.1f),
                                contentPadding = PaddingValues(0.dp)
                            ) {
                                Text("যোগ করুন +", fontSize = 9.sp, fontWeight = FontWeight.Bold)
                            }
                            Button(
                                onClick = {
                                    val amt = adjustAmountText.toDoubleOrNull() ?: 100.0
                                    viewModel.deductFundsFromUser(user.email, amt)
                                    adjustAmountText = ""
                                },
                                colors = ButtonDefaults.buttonColors(containerColor = FF_Red),
                                shape = RoundedCornerShape(8.dp),
                                modifier = Modifier.height(34.dp).weight(1.1f),
                                contentPadding = PaddingValues(0.dp)
                            ) {
                                Text("কর্তন করুন -", fontSize = 9.sp, fontWeight = FontWeight.Bold)
                            }
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        // General quick actions
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            Button(
                                onClick = { viewModel.addTestFundsToUser(user.email, 500.0) },
                                colors = ButtonDefaults.buttonColors(containerColor = Color.White.copy(alpha = 0.1f)),
                                modifier = Modifier.weight(1f)
                            ) {
                                Text("+ ৫০০৳ উপহার", fontSize = 10.sp)
                            }

                            if (!user.isAdmin) {
                                Button(
                                    onClick = { viewModel.makeUserAdmin(user.email) },
                                    colors = ButtonDefaults.buttonColors(containerColor = FF_Orange),
                                    modifier = Modifier.weight(1f)
                                ) {
                                    Text("এডমিন বানান", fontSize = 10.sp)
                                }
                            }
                        }
                    }
                }
            }
                }
            }
        }
    }
}

// ==========================================
// HEPER COMPONENT REUSABLES
// ==========================================
@Composable
fun GameLogoHeader() {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Box(
            modifier = Modifier
                .background(FF_Orange_Light, RoundedCornerShape(16.dp))
                .border(1.dp, FF_Orange, RoundedCornerShape(16.dp))
                .padding(horizontal = 24.dp, vertical = 14.dp)
        ) {
            Text(
                text = "BOOYAH!",
                color = FF_Orange,
                fontSize = 32.sp,
                fontWeight = FontWeight.Black,
                letterSpacing = 4.sp
            )
        }
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = "FF TOURNAMENT ARENA BD",
            color = FF_Orange,
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 2.sp
        )
    }
}



@Composable
fun HomeHeaderSection(user: UserEntity?, viewModel: MainViewModel) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                Brush.verticalGradient(
                    colors = listOf(FF_Orange_Light, FF_Dark_Bg)
                )
            )
            .padding(16.dp)
            .padding(top = 24.dp)
    ) {
        Column {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    val statusLabel = if (user?.email == "bd1rakib6677@gmail.com" || user?.isAdmin == true) "🌟 রিয়েল অ্যাকাউন্ট" else "🧪 টেস্ট ডেমো অ্যাকাউন্ট"
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text("হ্যালো গেমার,", color = Color.White.copy(alpha = 0.8f), fontSize = 11.sp)
                        Spacer(modifier = Modifier.width(6.dp))
                        Surface(
                            color = if (statusLabel.startsWith("🌟")) FF_Orange_Light else FF_Card_Bg_Tinted,
                            shape = RoundedCornerShape(4.dp),
                            modifier = Modifier.padding(top = 1.dp)
                        ) {
                            Text(
                                text = statusLabel,
                                color = if (statusLabel.startsWith("🌟")) FF_Yellow else Color.LightGray,
                                fontSize = 8.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp)
                            )
                        }
                    }
                    Text(user?.fullName ?: "প্লেয়ার", color = Color.White, fontSize = 20.sp, fontWeight = FontWeight.Black)
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    // Balance capsule
                    Surface(
                        color = Color.Black.copy(alpha = 0.6f),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.clickable { viewModel.currentScreen = Screen.Wallet }
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                        ) {
                            Text("💰 ", fontSize = 11.sp)
                            Text("${user?.balance ?: 0.0}৳", color = FF_Yellow, fontSize = 14.sp, fontWeight = FontWeight.Bold)
                        }
                    }

                    Spacer(modifier = Modifier.width(8.dp))

                    TextButton(onClick = { viewModel.logout() }) {
                        Text("লগআউট", color = Color.White, fontSize = 11.sp)
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // SLIDER BANNER CARD with ambient pulsing outer-glow
            val infinitePulseTransition = rememberInfiniteTransition(label = "banner_ambient_pulse")
            val pulsingBorderColor by infinitePulseTransition.animateColor(
                initialValue = FF_Orange.copy(alpha = 0.3f),
                targetValue = FF_Yellow.copy(alpha = 0.9f),
                animationSpec = infiniteRepeatable(
                    animation = tween(durationMillis = 1800, easing = LinearEasing),
                    repeatMode = RepeatMode.Reverse
                ),
                label = "banner_border"
            )

            val pulsatingScale by infinitePulseTransition.animateFloat(
                initialValue = 0.95f,
                targetValue = 1.05f,
                animationSpec = infiniteRepeatable(
                    animation = tween(durationMillis = 1200, easing = FastOutSlowInEasing),
                    repeatMode = RepeatMode.Reverse
                ),
                label = "scale_pulse"
            )

            Surface(
                color = FF_Card_Bg,
                shape = RoundedCornerShape(16.dp),
                border = BorderStroke(1.2.dp, pulsingBorderColor),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "🔥 সাপ্তাহিক গ্র্যান্ড লীগ টুর্নামেন্ট", 
                            color = FF_Yellow, 
                            fontSize = 11.sp, 
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 1.sp
                        )
                        // Pulsing status dot
                        Box(
                            modifier = Modifier
                                .size(8.dp)
                                .clip(RoundedCornerShape(4.dp))
                                .background(FF_Orange)
                        )
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "BOOYAH করে জিতে নিন ১০০০৳ পর্যন্ত নগদ পুরস্কার!", 
                        color = Color.White, 
                        fontSize = 15.sp, 
                        fontWeight = FontWeight.Black
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "প্লেয়ারদের কিল প্রতি থাকছে আলাদা ১০৳ বোনাস। যুক্ত হোন আজই!", 
                        color = Color.LightGray, 
                        fontSize = 11.sp,
                        lineHeight = 15.sp
                    )
                }
            }
        }
    }
}

@Composable
fun TournamentFilterRow(currentFilter: String, onFilterChanged: (String) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .horizontalScroll(rememberScrollState())
            .padding(horizontal = 16.dp, vertical = 10.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        listOf("ALL" to "সব টুর্নামেন্ট", "SOLO" to "Solo", "DUO" to "Duo", "SQUAD" to "Squad", "MY" to "নিবন্ধিত", "PROFILE" to "👤 আমার প্রোফাইল").forEach { (filterVal, label) ->
            val isSelected = currentFilter == filterVal
            
            // Beautiful interactive click animations
            val bgAnim by animateColorAsState(
                targetValue = if (isSelected) FF_Orange_Light else FF_Card_Bg,
                animationSpec = tween(durationMillis = 350, easing = FastOutSlowInEasing),
                label = "tab_bg"
            )
            val borderAnim by animateColorAsState(
                targetValue = if (isSelected) FF_Orange else Color.Gray.copy(alpha = 0.25f),
                animationSpec = tween(durationMillis = 350, easing = FastOutSlowInEasing),
                label = "tab_border"
            )
            
            Surface(
                color = bgAnim,
                shape = RoundedCornerShape(20.dp),
                border = BorderStroke(1.dp, borderAnim),
                modifier = Modifier.clickable { onFilterChanged(filterVal) }
            ) {
                Text(
                    text = label,
                    color = if (isSelected) Color.White else FF_Text_Secondary,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(horizontal = 14.dp, vertical = 6.dp)
                )
            }
        }
    }
}

@Composable
fun TournamentCard(
    tournament: TournamentEntity,
    isJoined: Boolean,
    onJoinClick: () -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    
    // Smooth border color state transitions matching the expansion
    val borderThickness by animateDpAsState(
        targetValue = if (expanded) 1.5.dp else 1.dp,
        animationSpec = tween(300),
        label = "thickness_anim"
    )
    val borderColorAnim by animateColorAsState(
        targetValue = if (expanded) FF_Orange else Color.Gray.copy(alpha = 0.15f),
        animationSpec = tween(300),
        label = "border_color_anim"
    )

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { expanded = !expanded },
        colors = CardDefaults.cardColors(containerColor = FF_Card_Bg),
        shape = RoundedCornerShape(12.dp),
        border = BorderStroke(borderThickness, borderColorAnim)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // Card Title + mode badge
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = tournament.title,
                        color = Color.White,
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Black
                    )
                    Text(
                        text = "ম্যাপ: ${tournament.map} | ডেট ও টাইম: ${tournament.date} @ ${tournament.time}",
                        color = FF_Text_Secondary,
                        fontSize = 11.sp
                    )
                }

                Spacer(modifier = Modifier.width(8.dp))

                Surface(
                    color = FF_Orange.copy(alpha = 0.15f),
                    shape = RoundedCornerShape(4.dp),
                    border = BorderStroke(1.dp, FF_Orange)
                ) {
                    Text(
                        text = tournament.gameMode,
                        color = FF_Orange,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Details Stats Grid
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.Black.copy(alpha = 0.3f), RoundedCornerShape(8.dp))
                    .padding(12.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("💰 মোট প্রাইজ পুল", color = Color.Gray, fontSize = 10.sp)
                    Text("${tournament.prizePool}৳", color = FF_Yellow, fontSize = 14.sp, fontWeight = FontWeight.Bold)
                }
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("🏆 BOOYAH উইন", color = Color.Gray, fontSize = 10.sp)
                    Text("${tournament.booyahPrize}৳", color = Color.White, fontSize = 14.sp, fontWeight = FontWeight.Bold)
                }
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("🔫 কিল বোনাস", color = Color.Gray, fontSize = 10.sp)
                    Text("${tournament.perKillPrize}৳", color = Color.White, fontSize = 14.sp, fontWeight = FontWeight.Bold)
                }
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("💵 এন্ট্রি Fee", color = Color.Gray, fontSize = 10.sp)
                    Text("${tournament.entryFee}৳", color = FF_Orange, fontSize = 14.sp, fontWeight = FontWeight.Bold)
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Slot occupancy indicator
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f).padding(end = 12.dp)) {
                    // Linear progress mapping registered slots (simulated for aesthetic richness)
                    val joinedMock = if (tournament.status == "COMPLETED") tournament.maxPlayers else if (tournament.id == 2) 20 else 14
                    val ratio = joinedMock.toFloat() / tournament.maxPlayers.toFloat()
                    LinearProgressIndicator(
                        progress = { ratio },
                        modifier = Modifier.fillMaxWidth().height(6.dp).clip(RoundedCornerShape(3.dp)),
                        color = FF_Orange,
                        trackColor = Color.White.copy(alpha = 0.1f)
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "ফিলাপ স্পট: ${joinedMock}/${tournament.maxPlayers}",
                        color = FF_Text_Secondary,
                        fontSize = 10.sp
                    )
                }

                // JOIN BTN
                if (tournament.status == "COMPLETED") {
                    Button(
                        onClick = {},
                        enabled = false,
                        colors = ButtonDefaults.buttonColors(disabledContainerColor = Color.Gray.copy(alpha = 0.2f))
                    ) {
                        Text("সম্পন্ন হয়েছে", color = Color.DarkGray, fontSize = 12.sp)
                    }
                } else if (isJoined) {
                    Button(
                        onClick = {},
                        colors = ButtonDefaults.buttonColors(containerColor = FF_Green.copy(alpha = 0.2f)),
                        border = BorderStroke(1.dp, FF_Green)
                    ) {
                        Text("🎮 যুক্ত আছেন", color = FF_Green, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                    }
                } else {
                    Button(
                        onClick = onJoinClick,
                        colors = ButtonDefaults.buttonColors(containerColor = FF_Orange),
                        shape = RoundedCornerShape(6.dp)
                    ) {
                        Text("অংশগ্রহণ করুন", color = Color.White, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }

            // Interactive expand collapse layout
            AnimatedVisibility(
                visible = expanded,
                enter = expandVertically() + fadeIn(),
                exit = shrinkVertically() + fadeOut()
            ) {
                Column(modifier = Modifier.padding(top = 12.dp)) {
                    Box(modifier = Modifier.fillMaxWidth().height(1.dp).background(Color.Gray.copy(alpha = 0.15f)))
                    Spacer(modifier = Modifier.height(10.dp))
                    Text("📢 টুর্নামেন্ট বিস্তারিত বিবরণ ও নিয়মাবলী:", color = FF_Yellow, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        "• ম্যাপ: ${tournament.map.uppercase()}\n" +
                        "• ম্যাচ টাইপ: ${tournament.gameMode}\n" +
                        "• হ্যাক বা এমুলেটর ব্যবহার সম্পূর্ণ নিষিদ্ধ এবং করলে একাউন্ট পার্মানেন্ট ব্যান করা হবে।\n" +
                        "• প্রতি কিলের জন্য পুরস্কার পাবেন ${tournament.perKillPrize} টাকা এবং চিকেন ডিনার বা Booyah করলে পাবেন ${tournament.booyahPrize} টাকা পুরস্কার।\n" +
                        "• ম্যাচ শুরুর ১৫ মিনিট আগে ওল্ড বা নিউ পাসওয়ার্ড 'আমার ম্যাচ' পেজে পাওয়া যাবে।",
                        color = Color.LightGray,
                        fontSize = 11.sp,
                        lineHeight = 16.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = if (expanded) "বিস্তারিত বন্ধ করুন 🔼" else "বিস্তারিত নিয়মাবলী দেখতে চাপুন 🔽",
                    color = FF_Orange.copy(alpha = 0.8f),
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

// ==========================================
// 7. DIALOG COMPONENT FOR JOIN MATCH
// ==========================================
@Composable
fun JoinTournamentDialog(
    tournament: TournamentEntity,
    user: UserEntity?,
    onDismiss: () -> Unit,
    onConfirmJoin: (uid: String, ign: String) -> Unit
) {
    var ffUid by remember { mutableStateOf(user?.ffUid ?: "") }
    var ffIgn by remember { mutableStateOf(user?.ffIgn ?: "") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Column {
                Text("টুর্নামেন্টে এন্ট্রি নিশ্চিত করুন", color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                Text(tournament.title, color = FF_Yellow, fontSize = 12.sp)
            }
        },
        text = {
            Column(modifier = Modifier.fillMaxWidth()) {
                Text("আপনার বর্তমান ব্যালেন্স: ${user?.balance ?: 0.0}৳", color = Color.LightGray, fontSize = 12.sp)
                Text("এন্ট্রি ফি কাটবে: ${tournament.entryFee}৳", color = FF_Orange, fontSize = 12.sp, fontWeight = FontWeight.Bold)

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = ffUid,
                    onValueChange = { ffUid = it },
                    label = { Text("ফ্রী ফায়ার UID নাম্বার", color = FF_Text_Secondary) },
                    colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = FF_Orange),
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(10.dp))

                OutlinedTextField(
                    value = ffIgn,
                    onValueChange = { ffIgn = it },
                    label = { Text("ফ্রী ফায়ার IGN (গেম নাম)", color = FF_Text_Secondary) },
                    colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = FF_Orange),
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text("*ভুল নাম বা আইডি দিলে খেলা বাতিল হতে পারে। রুম আইডি দিয়ে যুক্ত হওয়ার জন্য আপনার আসল গেম আইডি প্রদান করুন।*", color = Color.DarkGray, fontSize = 10.sp)
            }
        },
        confirmButton = {
            Button(
                onClick = { onConfirmJoin(ffUid, ffIgn) },
                colors = ButtonDefaults.buttonColors(containerColor = FF_Orange)
            ) {
                Text("নিশ্চিত ও পরিশোধ")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss, colors = ButtonDefaults.textButtonColors(contentColor = Color.White)) {
                Text("রদ করুন")
            }
        },
        containerColor = FF_Card_Bg,
        shape = RoundedCornerShape(16.dp)
    )
}

// Utility resolver colors
fun walletMethodColor(method: String): Color {
    return when (method) {
        "bKash" -> BKash_Pink
        "Nagad" -> Nagad_Orange
        else -> Rocket_Purple
    }
}

@Composable
fun GamerProfileSection(
    user: UserEntity?,
    registrations: List<RegistrationEntity>,
    viewModel: MainViewModel
) {
    if (user == null) return

    val totalMatches = registrations.size
    val totalKills = registrations.sumOf { it.kills }
    val totalWinnings = registrations.sumOf { it.rewardEarned }
    val booyahs = registrations.count { it.rank == 1 }

    // Dynamic Rank resolver
    val rankTitle = when {
        totalWinnings >= 1500.0 -> "GRANDMASTER 🏆"
        totalWinnings >= 800.0 -> "HEROIC 🔥"
        totalWinnings >= 400.0 -> "DIAMOND IV 💎"
        totalWinnings >= 150.0 -> "GOLD II 🎖️"
        else -> "BRONZE I 🏅"
    }

    var showEditDialog by remember { mutableStateOf(false) }

    var editFullName by remember { mutableStateOf(user.fullName) }
    var editPhone by remember { mutableStateOf(user.phone) }
    var editUid by remember { mutableStateOf(user.ffUid) }
    var editIgn by remember { mutableStateOf(user.ffIgn) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        // GEOMETRIC AVATAR CARD
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = FF_Card_Bg),
            border = BorderStroke(1.dp, FF_Orange.copy(alpha = 0.5f)),
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(
                modifier = Modifier.padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .size(80.dp)
                        .background(FF_Orange_Light, RoundedCornerShape(40.dp))
                        .border(2.dp, FF_Orange, RoundedCornerShape(40.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Text("🎮", fontSize = 36.sp)
                }

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = user.ffIgn.uppercase(),
                    color = Color.White,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Black,
                    letterSpacing = 1.sp
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = rankTitle,
                    color = FF_Yellow,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.sp
                )

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    // Quick edit button
                    Button(
                        onClick = { showEditDialog = true },
                        colors = ButtonDefaults.buttonColors(containerColor = FF_Orange_Light),
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("প্রোফাইল সংশোধন ✏️", color = Color.White, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                    }

                    Button(
                        onClick = { viewModel.addTestFundsToUser(user.email, 500.0) },
                        colors = ButtonDefaults.buttonColors(containerColor = FF_Orange.copy(alpha = 0.2f)),
                        border = BorderStroke(1.dp, FF_Orange),
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("এড ফান্ড +৫০০৳", color = Color.White, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // GAMING STATS TITLE
        Text(
            text = "🏆 আমার গেমার পরিসংখ্যান", 
            color = Color.White, 
            fontSize = 16.sp, 
            fontWeight = FontWeight.Bold,
            letterSpacing = 0.5.sp
        )
        Text(
            text = "BOOYAH Arena অটোমেটিক লিডারবোর্ড ট্র্যাকিং", 
            color = Color.Gray, 
            fontSize = 11.sp
        )

        Spacer(modifier = Modifier.height(12.dp))

        // STATS MULTI-COLS GRID
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            listOf(
                "ম্যাচ খেলেছেন" to "${totalMatches} টি",
                "মোট কিল সংখ্যা" to "${totalKills} টি"
            ).forEach { (label, value) ->
                Card(
                    modifier = Modifier.weight(1f),
                    colors = CardDefaults.cardColors(containerColor = FF_Card_Bg),
                    border = BorderStroke(1.dp, Color.Gray.copy(alpha = 0.15f))
                ) {
                    Column(
                        modifier = Modifier.padding(14.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(label, color = Color.LightGray, fontSize = 11.sp)
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(value, color = FF_Orange, fontSize = 20.sp, fontWeight = FontWeight.Black)
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            listOf(
                "BOOYAH জিতেছেন" to "${booyahs} বার",
                "অর্জিত প্রাইজমানি" to "${totalWinnings}৳"
            ).forEach { (label, value) ->
                Card(
                    modifier = Modifier.weight(1f),
                    colors = CardDefaults.cardColors(containerColor = FF_Card_Bg),
                    border = BorderStroke(1.dp, Color.Gray.copy(alpha = 0.15f))
                ) {
                    Column(
                        modifier = Modifier.padding(14.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(label, color = Color.LightGray, fontSize = 11.sp)
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(value, color = FF_Yellow, fontSize = 20.sp, fontWeight = FontWeight.Black)
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // ACCOUNTS INFO
        Text("⚙️ প্লেয়ার অ্যাকাউন্ট সিকিউরিটি ও তথ্য", color = Color.White, fontSize = 15.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(10.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = FF_Card_Bg),
            shape = RoundedCornerShape(12.dp),
            border = BorderStroke(1.dp, Color.Gray.copy(alpha = 0.1f))
        ) {
            Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text("নাম:", color = Color.Gray, fontSize = 12.sp)
                    Text(user.fullName, color = Color.White, fontSize = 13.sp, fontWeight = FontWeight.SemiBold)
                }
                Box(Modifier.fillMaxWidth().height(1.dp).background(Color.Gray.copy(alpha = 0.1f)))
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text("ইমেইল ঠিকানা:", color = Color.Gray, fontSize = 12.sp)
                    Text(user.email, color = Color.LightGray, fontSize = 13.sp)
                }
                Box(Modifier.fillMaxWidth().height(1.dp).background(Color.Gray.copy(alpha = 0.1f)))
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text("মোবাইল নাম্বার:", color = Color.Gray, fontSize = 12.sp)
                    Text(user.phone, color = Color.White, fontSize = 13.sp, fontWeight = FontWeight.SemiBold)
                }
                Box(Modifier.fillMaxWidth().height(1.dp).background(Color.Gray.copy(alpha = 0.1f)))
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text("ফ্রি ফায়ার প্লেয়ার UID:", color = Color.Gray, fontSize = 12.sp)
                    Text(user.ffUid, color = FF_Yellow, fontSize = 13.sp, fontWeight = FontWeight.Bold)
                }
                Box(Modifier.fillMaxWidth().height(1.dp).background(Color.Gray.copy(alpha = 0.1f)))
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text("গেম ইন-নেম (IGN):", color = Color.Gray, fontSize = 12.sp)
                    Text(user.ffIgn.uppercase(), color = FF_Orange, fontSize = 13.sp, fontWeight = FontWeight.Bold)
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))
    }

    // Edit Profile details action dialogue
    if (showEditDialog) {
        AlertDialog(
            onDismissRequest = { showEditDialog = false },
            title = {
                Text("ফ্রি ফায়ার অ্যাকাউন্ট সংশোধন ✏️", color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Bold)
            },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    OutlinedTextField(
                        value = editFullName,
                        onValueChange = { editFullName = it },
                        label = { Text("আপনার পুরো নাম") },
                        colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = FF_Orange),
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )

                    OutlinedTextField(
                        value = editPhone,
                        onValueChange = { editPhone = it },
                        label = { Text("মোবাইল নাম্বার") },
                        colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = FF_Orange),
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )

                    OutlinedTextField(
                        value = editUid,
                        onValueChange = { editUid = it },
                        label = { Text("ফ্রি ফায়ার UID") },
                        colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = FF_Orange),
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )

                    OutlinedTextField(
                        value = editIgn,
                        onValueChange = { editIgn = it },
                        label = { Text("গেম ইন-গেম নাম (IGN)") },
                        colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = FF_Orange),
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        viewModel.updateGamerProfile(editFullName, editPhone, editUid, editIgn) { ok ->
                            if (ok) {
                                showEditDialog = false
                            }
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = FF_Orange)
                ) {
                    Text("সংরক্ষণ করুন")
                }
            },
            dismissButton = {
                TextButton(onClick = { showEditDialog = false }, colors = ButtonDefaults.textButtonColors(contentColor = Color.White)) {
                    Text("বাতিল")
                }
            },
            containerColor = FF_Card_Bg,
            shape = RoundedCornerShape(16.dp)
        )
    }
}
