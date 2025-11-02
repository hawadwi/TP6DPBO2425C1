# TP6DPBO2425C1

Saya Hawa Dwiafina Azahra dengan NIM 2400336 mengerjakan Tugas Praktikum 5 dalam mata kuliah Desain dan Pemrograman Berorientasi Objek untuk keberkahanNya maka saya tidak melakukan kecurangan seperti yang telah dispesifikasikan. Aamiin.

## DESAIN PROGRAM

### 1. Class Player

Player merepresentasikan burung yang dimainkan pengguna. Setiap objek Player memiliki:
- `posX` (int) - Posisi horizontal burung di layar
- `posY` (int) - Posisi vertikal burung di layar
- `width` (int) - Lebar sprite burung (34 pixel)
- `height` (int) - Tinggi sprite burung (24 pixel)
- `image` (Image) - Gambar/sprite burung yang ditampilkan
- `velocityY` (int) - Kecepatan vertikal burung (untuk gravity dan flap effect)

Kelas ini menyediakan konstruktor dan getter/setter untuk semua atribut tanpa logika game.

### 2. Class Pipe

Pipe merepresentasikan pipa atas atau bawah yang menjadi rintangan. Setiap objek Pipe memiliki:
- `posX` (int) - Posisi horizontal pipa
- `posY` (int) - Posisi vertikal pipa
- `width` (int) - Lebar pipa (64 pixel)
- `height` (int) - Tinggi pipa (512 pixel)
- `image` (Image) - Gambar pipa atas atau bawah
- `velocityX` (int) - Kecepatan horizontal pipa bergerak ke kiri (-2)
- `passed` (boolean) - Flag untuk menandai apakah pipa sudah melewati player (untuk scoring)

Kelas ini menyediakan konstruktor dan getter/setter untuk semua atribut. Flag `passed` digunakan untuk memastikan skor bertambah hanya satu kali per pipa pair.

### 3. Kelas Logic

Logic mengatur semua mekanik permainan dan mengimplementasikan ActionListener dan KeyListener:

**Atribut Game State:**
- `frameWidth` (360) dan `frameHeight` (640) - Ukuran window permainan
- `player` (Player) - Object pemain
- `pipes` (ArrayList<Pipe>) - Daftar semua pipa di layar
- `gameStart` (int) - Status permainan: 0 = belum dimulai, 1 = sedang bermain
- `gameOver` (int) - Status permainan: 0 = bermain, 1 = game over
- `score` (int) - Skor pemain

**Atribut Physics:**
- `gravity` (int = 1) - Percepatan gravitasi vertikal setiap frame
- `pipeVelocityX` (int = -2) - Kecepatan pipa bergerak ke kiri
- `playerStartPosX/Y` - Posisi starting pemain di tengah layar

**Timer:**
- `gameLoop` (Timer) - Timer utama permainan yang berjalan 60 FPS (1000/60 ms)
- `pipesCooldown` (Timer) - Timer yang menghasilkan pipa baru setiap 2 detik

**Metode Utama:**
- `setView(View view)` - Menghubungkan Logic dengan View untuk rendering
- `startGame()` - Memulai permainan (start timers, set gameStart = 1)
- `placePipes()` - Membuat pasang pipa baru dengan gap random untuk dimainkan
- `move()` - Update posisi player dan pipe, check collision dan scoring
- `checkCollisionWithPipes()` - Collision detection antara player dan pipa menggunakan Rectangle
- `isPlayerOutOfBounds()` - Cek apakah player jatuh keluar layar (kalah)
- `checkPipePassed()` - Increment score jika player berhasil melewati pipa
- `endGame()` - Berhentikan permainan, stop timers
- `restart()` - Reset semua state untuk permainan baru

### 4. Class View

View adalah JPanel yang bertanggung jawab menampilkan seluruh game visual:

**Atribut:**
- `logic` (Logic) - Referensi ke Logic untuk mengambil data game state
- `bgImage` (Image) - Background image permainan 
- `width` (360) dan `height` (640) - Dimensi panel

**Fitur:**
- Menampilkan background image
- Menampilkan player sprite pada posisinya
- Menampilkan semua pipa dari ArrayList
- Menampilkan score di sudut kiri atas
- Menampilkan "GAME OVER!" dan "Press R to Restart" saat permainan berakhir
- Menambahkan KeyListener dari Logic untuk input pemain

Kelas ini memanfaatkan `paintComponent()` yang dipanggil secara otomatis oleh repaint() timer.

### 5. Class FlappyBirdForm

FlappyBirdForm adalah jendela utama yang menampilkan menu start/exit sebelum permainan dimulai:

**Komponen GUI:**
- `label` - Label "PRESS TO START" di bagian atas
- `buttonStart` - Tombol START dengan custom paint (hijau dengan hover effect)
- `buttonExit` - Tombol EXIT dengan custom paint (merah dengan hover effect)
- `view` - Panel game View yang di-overlay
- `panel1` - Container utama menggunakan OverlayLayout untuk menempatkan button panel di atas View

**Fitur:**
- Ukuran window 360x640 pixel (fixed size)
- Saat START ditekan: sembunyikan tombol dan label, mulai game, request focus ke view
- Saat EXIT ditekan: tutup aplikasi
- Background cyan sebelum game dimulai

### 6. Class App

App adalah kelas main yang meluncurkan aplikasi:
- Membuat JFrame dengan ukuran 360x460 pixel
- Membuat Logic dan View
- Menghubungkan Logic ke View
- Menampilkan window di layar


## ALUR PROGRAM

### Inisialisasi 

**App.main():**
1. Buat JFrame dengan judul "Flappy Bird", ukuran 360x460
2. Set lokasi ke tengah layar
3. Buat instance Logic dan View
4. Hubungkan logic ke view dengan `setView()`
5. Request focus ke view (agar keyboard input bekerja)
6. Tambahkan view ke frame, pack, dan tampilkan

**FlappyBirdForm (Alternatif):**
Jika menggunakan FlappyBirdForm, proses serupa tapi dengan menu awal:
1. Buat JFrame ukuran 360x640 dengan OverlayLayout
2. Buat Logic dan View
3. Hubungkan Logic ke View
4. Tampilkan label "PRESS TO START"
5. Tampilkan tombol START dan EXIT di atas View

### Menu/Screen Awal (Sebelum Game Dimulai)

**State:** `gameStart = 0`, `gameOver = 0`

1. **Tampilan:**
   - Background cyan atau View dengan background image
   - Label "PRESS TO START" di tengah
   - Tombol START dan EXIT

2. **Input User:**
   - Click START button → Panggil `logic.startGame()`
   - Click EXIT button → `System.exit(0)`

3. **Action: startGame()**
   - Cek apakah `gameStart == 0`
   - Set `gameStart = 1`
   - Start `pipesCooldown` timer (generate pipa setiap 2 detik)
   - Start `gameLoop` timer (update game 60 FPS)
   - Sembunyikan tombol START dan EXIT
   - Sembunyikan label "PRESS TO START"
   - Request focus ke View (agar keyboard input bekerja)

### Game Loop (Game Sedang Berlangsung)

**Frequency:** 60 FPS (setiap 1000/60 = ~16.67 ms)

**gameLoop.actionPerformed():**
1. Panggil `move()` untuk update game state
2. Panggil `view.repaint()` untuk redraw semua elemen
3. View akan memanggil `draw()` yang menampilkan:
   - Background image
   - Player sprite
   - Semua Pipe dari ArrayList
   - Score text di atas
   - Jika gameOver: tampilkan "GAME OVER!" dan "Press R to Restart"

### Movement (dalam move())

**Gravity & Player Movement:**
1. Jika `gameStart == 1` dan `gameOver == 0`:
   - Tambahkan gravity ke `velocityY`: `player.velocityY += gravity`
   - Tambahkan velocity ke posisi: `player.posY += player.velocityY`
   - Clamp posisi agar tidak keluar atas: `player.posY = max(player.posY, 0)`

2. **Pipe Movement:**
   - Loop semua pipe di ArrayList
   - Geser ke kiri: `pipe.posX += pipeVelocityX` (bernilai -2)

3. **Collision Detection:**
   - Call `checkCollisionWithPipes()` menggunakan Rectangle.intersects()
   - Jika collision true → `endGame()`

4. **Out of Bounds Check:**
   - Call `isPlayerOutOfBounds()` jika `player.posY + player.height > frameHeight`
   - Jika true → `endGame()`

5. **Scoring:**
   - Call `checkPipePassed()` untuk setiap pipe
   - Jika `pipe.posX + pipe.width < player.posX` dan `!pipe.isPassed()`
   - Set `pipe.passed = true` dan increment score (hanya untuk lower pipe)

### Pipe Generation (pipesCooldown - Setiap 2 Detik)

**pipesCooldown.actionPerformed():**
1. Generate `randomPosY` untuk upper pipe dengan variasi random
   - Upper pipe Y position: `randomPosY = pipeStartPosY - pipeHeight/4 - Math.random() * (pipeHeight/2)`
2. Set opening space antar pipa: `openingSpace = frameWidth/2` (180 pixel)
3. Buat upper pipe baru: `new Pipe(pipeStartPosX, randomPosY, ...)`
4. Tambahkan ke ArrayList
5. Buat lower pipe baru: `new Pipe(pipeStartPosX, randomPosY + openingSpace + pipeHeight, ...)`
6. Tambahkan ke ArrayList
7. Kedua pipe akan bergerak ke kiri di game loop berikutnya

### Player Input (Keyboard)

**FlappingMechanic:**
- Input: Tekan tombol UP ARROW
- Condition: Hanya bekerja jika `gameStart == 1` dan `gameOver == 0`
- Action: Set `player.velocityY = -10` (impulse ke atas, melawan gravity)

**Restart:**
- Input: Tekan tombol R
- Condition: Hanya bekerja jika `gameOver == 1`
- Action: Panggil `restart()`

### Game Over State

**Trigger GameOver:**
1. Player collision dengan pipe → `checkCollisionWithPipes()` return true
2. Player jatuh keluar layar → `isPlayerOutOfBounds()` return true
3. `endGame()` dipanggil:
   - Set `gameOver = 1`
   - Stop `gameLoop` timer
   - Stop `pipesCooldown` timer
   - Cetak "GAME OVER!" ke console

**Display Game Over:**
- View.draw() mendeteksi `gameOver != 0`
- Gambar teks besar "GAME OVER!" di tengah layar
- Gambar teks "Press R to Restart" di bawahnya

**Restart Permainan:**
1. User tekan R → KeyListener call `restart()`
2. Cek apakah `gameOver != 0`
3. Reset state:
   - Set `gameOver = 0`
   - Reset `player` ke posisi start tengah layar
   - Set `player.velocityY = 0`
   - Clear `pipes` ArrayList
4. Buat `pipesCooldown` timer baru
5. Start `pipesCooldown` dan `gameLoop` kembali
6. Cetak "GAME RESTARTED!" ke console
7. Score TETAP tersimpan dari permainan sebelumnya (jika ingin reset, tambahkan `score = 0`)

### Scoring System

**Rule:**
- Score increment hanya saat player melewati pipa (dari kiri ke kanan)
- Hanya lower pipe yang di-count (index ganjil dalam ArrayList)
- Menggunakan flag `pipe.passed` untuk memastikan hanya count sekali per pipe

**Logic:**
1. Cek setiap pipe: `pipe.posX + pipe.width < player.posX`
2. Dan `!pipe.isPassed()` (belum pernah di-count)
3. Set `pipe.passed = true`
4. Jika ini lower pipe (index % 2 == 1), increment score
5. Cetak score ke console

## DOKUMENTASI

### START & RESTART

![dokumentasi](dokumentasi/START&RESTART.gif)

### EXIT

![dokumentasi](dokumentasi/EXIT.gif)



