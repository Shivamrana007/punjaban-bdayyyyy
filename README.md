# 💖 Happy Birthday, My Babygirl Punjaban 🌹

A responsive, highly interactive multi-page birthday experience crafted with romantic aesthetics, 3D animations, wax-sealed love letters, continuous floating hearts and rose petals, and a modular architecture.

---

## 📁 Project Architecture & Separated Files

```
k:/bybd/
│
├── index.html                  # Main HTML markup with all 7 views & setup modal
├── BirthdayServer.java         # Standalone Java HTTP Backend Server (Zero dependencies)
├── run.bat                     # Windows 1-click launcher (starts Java server or opens browser)
│
├── css/                        # 🎨 Modular Stylesheets
│   ├── main.css                # Base theme, typography, modal, buttons, progress meter
│   ├── animations.css          # Romantic keyframes, glowing text, heartbeats, shimmers
│   ├── carousel.css            # 3D Rotating cylinder carousel & polaroid grid gallery
│   └── envelope.css            # 3D Realistic baby-pink envelopes, wax seals & letters
│
└── js/                         # ⚡ Modular JavaScript Files
    ├── particles.js            # Floating hearts, falling rose petals, stardust & confetti
    ├── audio.js                # Web Audio API romantic melody synthesizer & sound chimes
    ├── carousel.js             # 3D carousel rotator, alignment & photo modal inspector
    ├── envelope.js             # Wax unsealing physics, letter card sliding & grand finale
    └── app.js                  # Setup uploader, 5s countdown, view router & Java sync
```

---

## 🚀 How to Run

### Option 1: Double-Click the Launcher
Simply double-click **`run.bat`**:
- If Java (JDK 8+) is installed, it compiles and starts `BirthdayServer.java` on `http://localhost:8080` and auto-opens your browser.
- If Java is not in your PATH, it automatically opens `index.html` directly in your default web browser!

### Option 2: Run via Java Server
In terminal/PowerShell:
```bash
javac BirthdayServer.java
java BirthdayServer
```
Then visit: `http://localhost:8080`

### Option 3: Direct Browser Launch (No server required)
Just double-click **`index.html`** or open it in any browser (Chrome, Edge, Safari, Firefox). All audio, particle systems, and 3D animations work 100% client-side!

---

## 🌸 Visual Theme & Experience Flow
1. **Pre-load Setup**: Upload 6 personal photos or start with romantic defaults; customize heartfelt captions.
2. **Page 1 (Loading)**: 5-second heart progress bar with glowing text and pulsing reveal button.
3. **Page 2 (3D Gallery)**: Rotating cylinder carousel -> "Align Our Memories" smooth transition to asymmetrical polaroids with click-to-flip love notes.
4. **Pages 3-7 (Love Envelopes)**: 5 realistic baby-pink envelopes with wax seals (🌹, 👑, 💝, ♾️, 🎂), realistic flap opening, and sliding love letters.
5. **Finale (Page 7)**: Celebration explosion with confetti, floating hearts, and romantic birthday banner.
6. **Ambient Controls**: Top-right music button (soft romantic melody) + bottom-right 💖 burst FAB button.
