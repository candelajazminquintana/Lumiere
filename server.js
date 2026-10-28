const http = require('http');
const fs = require('fs');
const path = require('path');

const PORT = 3000;
const APK_PATH = path.join(__dirname, 'app/build/outputs/apk/debug/app-debug.apk');
const LOGO_PATH = path.join(__dirname, 'app/src/main/res/drawable/lumiere_logo.jpg');

const HTML_CONTENT = `<!DOCTYPE html>
<html lang="es">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
  <title>LUMIÈRE Peluquería</title>
  <link rel="preconnect" href="https://fonts.googleapis.com">
  <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
  <link href="https://fonts.googleapis.com/css2?family=Great+Vibes&family=Montserrat:wght@300;400;500;600;700&family=Playfair+Display:ital,wght@0,600;0,700;1,600&display=swap" rel="stylesheet">
  <style>
    :root {
      --bg-cream: #FAF5EE;
      --surface-cream: #FFFDF9;
      --marsala-primary: #8B3A4F;
      --marsala-dark: #6B2537;
      --rose-accent: #B76E79;
      --rose-soft: #EAD8DC;
      --rose-pill: #F3E7EA;
      --gold-accent: #C89D66;
      --text-dark: #231F20;
      --text-muted: #746B6E;
      --whatsapp-green: #25D366;
      --whatsapp-green-hover: #1EBE5D;
    }

    * {
      box-sizing: border-box;
      margin: 0;
      padding: 0;
      -webkit-tap-highlight-color: transparent;
    }

    body {
      background: #141214;
      font-family: 'Montserrat', sans-serif;
      min-height: 100vh;
      display: flex;
      flex-direction: column;
      align-items: center;
      justify-content: center;
      padding: 16px;
      color: #fff;
    }

    .top-controls {
      display: flex;
      gap: 12px;
      margin-bottom: 16px;
      flex-wrap: wrap;
      justify-content: center;
    }

    .btn-download {
      display: inline-flex;
      align-items: center;
      gap: 8px;
      background: linear-gradient(135deg, #8B3A4F, #B76E79);
      color: #fff;
      padding: 10px 20px;
      border-radius: 24px;
      font-size: 13.5px;
      font-weight: 600;
      text-decoration: none;
      box-shadow: 0 4px 15px rgba(139, 58, 79, 0.4);
      transition: transform 0.2s, box-shadow 0.2s;
    }

    .btn-download:hover {
      transform: translateY(-2px);
      box-shadow: 0 6px 20px rgba(139, 58, 79, 0.6);
    }

    /* Device frame */
    .device-wrapper {
      width: 100%;
      max-width: 390px;
      height: 780px;
      background: #000;
      border-radius: 48px;
      padding: 11px;
      box-shadow: 0 25px 60px rgba(0, 0, 0, 0.8), 0 0 0 2px #333;
      position: relative;
    }

    .device-screen {
      width: 100%;
      height: 100%;
      background: var(--bg-cream);
      border-radius: 38px;
      overflow: hidden;
      display: flex;
      flex-direction: column;
      position: relative;
    }

    /* Status Bar */
    .status-bar {
      height: 38px;
      padding: 0 22px;
      display: flex;
      justify-content: space-between;
      align-items: center;
      font-size: 13px;
      font-weight: 600;
      color: var(--text-dark);
      z-index: 10;
      background: transparent;
    }

    .island-notch {
      width: 90px;
      height: 20px;
      background: #000;
      border-radius: 14px;
      margin: 0 auto;
    }

    /* Screens Container */
    .screen-content {
      flex: 1;
      overflow-y: auto;
      display: none;
      flex-direction: column;
      position: relative;
    }

    .screen-content.active {
      display: flex;
    }

    /* SCREEN 1: INICIO */
    #pantalla1 {
      padding: 14px 22px 24px 22px;
      justify-content: space-between;
      align-items: center;
      text-align: center;
    }

    .logo-container {
      width: 130px;
      height: 130px;
      border-radius: 50%;
      border: 3px solid var(--gold-accent);
      box-shadow: 0 10px 25px rgba(139, 58, 79, 0.25);
      overflow: hidden;
      margin: 12px auto 16px auto;
      background: #fff;
    }

    .logo-container img {
      width: 100%;
      height: 100%;
      object-fit: cover;
    }

    .brand-title {
      font-family: 'Playfair Display', serif;
      font-size: 34px;
      font-weight: 700;
      letter-spacing: 4px;
      color: var(--text-dark);
      margin-bottom: 2px;
    }

    .brand-subtitle {
      font-family: 'Great Vibes', cursive;
      font-size: 30px;
      color: var(--marsala-primary);
      margin-bottom: 12px;
    }

    .heart-divider {
      display: flex;
      align-items: center;
      justify-content: center;
      width: 80%;
      margin: 8px auto 16px auto;
      gap: 12px;
    }

    .heart-divider::before,
    .heart-divider::after {
      content: "";
      flex: 1;
      height: 1px;
      background: var(--rose-soft);
    }

    .heart-divider span {
      color: var(--marsala-primary);
      font-size: 15px;
    }

    .pillars-grid {
      display: grid;
      grid-template-columns: repeat(3, 1fr);
      gap: 10px;
      width: 100%;
      margin: 10px 0 18px 0;
    }

    .pillar-card {
      background: var(--surface-cream);
      border: 1px solid var(--rose-soft);
      border-radius: 16px;
      padding: 12px 6px;
      display: flex;
      flex-direction: column;
      align-items: center;
      box-shadow: 0 2px 8px rgba(139, 58, 79, 0.06);
    }

    .pillar-icon-box {
      width: 42px;
      height: 42px;
      border-radius: 50%;
      background: rgba(234, 216, 220, 0.7);
      display: flex;
      align-items: center;
      justify-content: center;
      color: var(--marsala-primary);
      font-size: 18px;
      margin-bottom: 6px;
    }

    .pillar-name {
      font-size: 11px;
      font-weight: 700;
      letter-spacing: 0.5px;
      color: var(--marsala-primary);
      margin-bottom: 2px;
    }

    .pillar-desc {
      font-size: 9px;
      color: var(--text-muted);
      line-height: 12px;
    }

    .quote-card {
      background: var(--surface-cream);
      border: 1px solid var(--rose-soft);
      border-radius: 16px;
      padding: 12px 14px;
      margin-bottom: 14px;
      width: 100%;
    }

    .quote-title {
      font-size: 13px;
      font-weight: 600;
      color: var(--text-dark);
      margin-bottom: 3px;
    }

    .quote-text {
      font-size: 11px;
      color: var(--text-muted);
      line-height: 15px;
    }

    /* Buttons */
    .btn-action-marsala {
      width: 100%;
      height: 52px;
      background: var(--marsala-primary);
      color: #fff;
      border: none;
      border-radius: 28px;
      font-family: 'Montserrat', sans-serif;
      font-size: 14px;
      font-weight: 700;
      letter-spacing: 1px;
      cursor: pointer;
      display: flex;
      align-items: center;
      justify-content: center;
      gap: 10px;
      box-shadow: 0 6px 16px rgba(139, 58, 79, 0.35);
      transition: all 0.2s;
    }

    .btn-action-marsala:hover {
      background: var(--marsala-dark);
      transform: translateY(-1px);
    }

    /* SCREEN 2: PRECIOS */
    #pantalla2 {
      padding: 10px 18px 18px 18px;
    }

    .header-bar {
      display: flex;
      align-items: center;
      justify-content: space-between;
      margin-bottom: 12px;
      position: sticky;
      top: 0;
      background: var(--bg-cream);
      padding: 4px 0;
      z-index: 5;
    }

    .btn-icon-back {
      width: 38px;
      height: 38px;
      border-radius: 50%;
      background: rgba(234, 216, 220, 0.7);
      border: none;
      color: var(--marsala-primary);
      display: flex;
      align-items: center;
      justify-content: center;
      font-size: 16px;
      cursor: pointer;
    }

    .category-card {
      background: var(--surface-cream);
      border: 1.2px solid var(--rose-soft);
      border-radius: 16px;
      padding: 14px;
      margin-bottom: 12px;
      box-shadow: 0 2px 8px rgba(0,0,0,0.03);
    }

    .category-badge {
      display: inline-block;
      background: rgba(234, 216, 220, 0.7);
      color: var(--marsala-primary);
      font-family: 'Great Vibes', cursive;
      font-size: 22px;
      font-weight: bold;
      padding: 2px 14px;
      border-radius: 12px;
      margin-bottom: 10px;
    }

    .service-row {
      display: flex;
      align-items: center;
      justify-content: space-between;
      padding: 9px 8px;
      border-radius: 10px;
      cursor: pointer;
      transition: background 0.15s;
      border-bottom: 1px solid rgba(234, 216, 220, 0.4);
    }

    .service-row:last-child {
      border-bottom: none;
    }

    .service-row:hover,
    .service-row.selected {
      background: var(--rose-pill);
    }

    .service-info {
      display: flex;
      align-items: center;
      gap: 10px;
    }

    .check-circle {
      width: 20px;
      height: 20px;
      border-radius: 50%;
      border: 1.5px solid var(--rose-accent);
      display: flex;
      align-items: center;
      justify-content: center;
      font-size: 11px;
      color: #fff;
    }

    .service-row.selected .check-circle {
      background: var(--marsala-primary);
      border-color: var(--marsala-primary);
    }

    .service-name {
      font-size: 13.5px;
      color: var(--text-dark);
      font-weight: 500;
    }

    .service-price {
      font-size: 14.5px;
      font-weight: 700;
      color: var(--marsala-primary);
    }

    .note-version {
      font-family: 'Great Vibes', cursive;
      font-size: 26px;
      color: var(--marsala-primary);
      text-align: center;
      margin: 12px 0 16px 0;
    }

    /* Sticky Bottom Bar */
    .bottom-bar {
      background: #fff;
      padding: 10px 18px 14px 18px;
      box-shadow: 0 -4px 12px rgba(0, 0, 0, 0.06);
      display: flex;
      flex-direction: column;
      gap: 6px;
    }

    .selection-summary {
      display: flex;
      justify-content: space-between;
      font-size: 12.5px;
      color: var(--text-muted);
      font-weight: 600;
    }

    .selection-total {
      color: var(--marsala-primary);
      font-size: 14px;
      font-weight: 700;
    }

    /* SCREEN 3: CONTACTO */
    #pantalla3 {
      padding: 14px 22px 20px 22px;
      justify-content: space-between;
      align-items: center;
      text-align: center;
    }

    .contact-badge {
      background: var(--rose-soft);
      color: var(--marsala-primary);
      font-family: 'Great Vibes', cursive;
      font-size: 32px;
      font-weight: bold;
      padding: 6px 36px;
      border-radius: 24px;
      display: inline-block;
      margin: 14px 0 18px 0;
    }

    .phone-box {
      background: var(--surface-cream);
      border: 1.5px solid var(--rose-accent);
      border-radius: 20px;
      padding: 12px 20px;
      display: inline-flex;
      align-items: center;
      gap: 12px;
      margin-bottom: 14px;
      box-shadow: 0 4px 12px rgba(139, 58, 79, 0.08);
    }

    .phone-text {
      font-family: 'Great Vibes', cursive;
      font-size: 36px;
      font-weight: bold;
      color: var(--marsala-primary);
    }

    .btn-copy-small {
      background: none;
      border: none;
      color: var(--rose-accent);
      cursor: pointer;
      font-size: 16px;
      padding: 4px;
    }

    .hope-see-you {
      font-family: 'Great Vibes', cursive;
      font-size: 38px;
      color: var(--marsala-primary);
      margin: 10px 0;
    }

    .btn-whatsapp {
      width: 100%;
      height: 56px;
      background: var(--whatsapp-green);
      color: #fff;
      border: none;
      border-radius: 30px;
      font-family: 'Montserrat', sans-serif;
      font-size: 15px;
      font-weight: 700;
      letter-spacing: 0.5px;
      display: flex;
      align-items: center;
      justify-content: center;
      gap: 10px;
      text-decoration: none;
      box-shadow: 0 6px 18px rgba(37, 211, 102, 0.4);
      cursor: pointer;
      transition: all 0.2s;
    }

    .btn-whatsapp:hover {
      background: var(--whatsapp-green-hover);
      transform: translateY(-1px);
    }

    .secondary-actions {
      display: flex;
      gap: 10px;
      width: 100%;
      margin: 10px 0;
    }

    .btn-outlined-sec {
      flex: 1;
      height: 42px;
      background: transparent;
      border: 1px solid var(--rose-accent);
      color: var(--marsala-primary);
      border-radius: 20px;
      font-family: 'Montserrat', sans-serif;
      font-size: 12.5px;
      font-weight: 600;
      cursor: pointer;
      display: flex;
      align-items: center;
      justify-content: center;
      gap: 6px;
      text-decoration: none;
    }

    .btn-text-back {
      background: none;
      border: none;
      color: var(--text-muted);
      font-size: 12.5px;
      cursor: pointer;
      padding: 6px;
      text-decoration: underline;
    }
  </style>
</head>
<body>

  <div class="top-controls">
    <a href="/app-debug.apk" class="btn-download" download="Lumiere-Peluqueria.apk">
      <span>⬇</span> Descargar APK Android (23 MB)
    </a>
  </div>

  <div class="device-wrapper">
    <div class="device-screen">
      <!-- Status bar -->
      <div class="status-bar">
        <span>10:30</span>
        <div class="island-notch"></div>
        <span>5G 100%</span>
      </div>

      <!-- PANTALLA 1: INICIO -->
      <div id="pantalla1" class="screen-content active">
        <div></div>
        <div>
          <div class="logo-container">
            <img src="/logo.jpg" alt="Logo Lumière Peluquería">
          </div>
          <div class="brand-title">LUMIÈRE</div>
          <div class="brand-subtitle">— Peluquería —</div>

          <div class="heart-divider"><span>♡</span></div>

          <div class="pillars-grid">
            <div class="pillar-card">
              <div class="pillar-icon-box">〰</div>
              <div class="pillar-name">ALISADO</div>
              <div class="pillar-desc">Brillo y sedosidad</div>
            </div>
            <div class="pillar-card">
              <div class="pillar-icon-box">🎨</div>
              <div class="pillar-name">COLOR</div>
              <div class="pillar-desc">Tendencia y vida</div>
            </div>
            <div class="pillar-card">
              <div class="pillar-icon-box">✂</div>
              <div class="pillar-name">CORTE</div>
              <div class="pillar-desc">Estilo a medida</div>
            </div>
          </div>

          <div class="quote-card">
            <div class="quote-title">Tu cabello en las mejores manos</div>
            <div class="quote-text">Productos profesionales, asesoramiento personalizado y resultados que te encantarán.</div>
          </div>
        </div>

        <button class="btn-action-marsala" onclick="goTo('pantalla2')">
          VER SERVICIOS Y PRECIOS <span>→</span>
        </button>
      </div>

      <!-- PANTALLA 2: LISTA DE PRECIOS -->
      <div id="pantalla2" class="screen-content">
        <div class="header-bar">
          <button class="btn-icon-back" onclick="goTo('pantalla1')">←</button>
          <div style="text-align: center;">
            <div style="font-family: 'Playfair Display', serif; font-size: 19px; font-weight: 700; color: var(--text-dark);">LUMIÈRE</div>
            <div style="font-family: 'Great Vibes', cursive; font-size: 18px; color: var(--marsala-primary);">— Lista de Precios —</div>
          </div>
          <div style="width: 38px;"></div>
        </div>

        <!-- Alisado -->
        <div class="category-card">
          <div class="category-badge">Alisado ♡</div>
          <div class="service-row" onclick="toggleItem('Alisado corto', 25000, this)">
            <div class="service-info"><div class="check-circle">✓</div><span class="service-name">Alisado corto</span></div>
            <div class="service-price">$25.000</div>
          </div>
          <div class="service-row" onclick="toggleItem('Alisado media melena', 30000, this)">
            <div class="service-info"><div class="check-circle">✓</div><span class="service-name">Alisado media melena</span></div>
            <div class="service-price">$30.000</div>
          </div>
          <div class="service-row" onclick="toggleItem('Alisado largo', 35000, this)">
            <div class="service-info"><div class="check-circle">✓</div><span class="service-name">Alisado largo</span></div>
            <div class="service-price">$35.000</div>
          </div>
          <div class="service-row" onclick="toggleItem('Alisado extra largo', 40000, this)">
            <div class="service-info"><div class="check-circle">✓</div><span class="service-name">Alisado extra largo</span></div>
            <div class="service-price">$40.000</div>
          </div>
        </div>

        <!-- Color -->
        <div class="category-card">
          <div class="category-badge">Color ♡</div>
          <div class="service-row" onclick="toggleItem('Color completo', 30000, this)">
            <div class="service-info"><div class="check-circle">✓</div><span class="service-name">Color completo</span></div>
            <div class="service-price">$30.000</div>
          </div>
          <div class="service-row" onclick="toggleItem('Retoque de raíz', 20000, this)">
            <div class="service-info"><div class="check-circle">✓</div><span class="service-name">Retoque de raíz</span></div>
            <div class="service-price">$20.000</div>
          </div>
          <div class="service-row" onclick="toggleItem('Balayage / Babylights', 45000, this)">
            <div class="service-info"><div class="check-circle">✓</div><span class="service-name">Balayage / Babylights</span></div>
            <div class="service-price">$45.000</div>
          </div>
          <div class="service-row" onclick="toggleItem('Mechas tradicionales', 40000, this)">
            <div class="service-info"><div class="check-circle">✓</div><span class="service-name">Mechas tradicionales</span></div>
            <div class="service-price">$40.000</div>
          </div>
          <div class="service-row" onclick="toggleItem('Matización', 15000, this)">
            <div class="service-info"><div class="check-circle">✓</div><span class="service-name">Matización</span></div>
            <div class="service-price">$15.000</div>
          </div>
        </div>

        <!-- Corte -->
        <div class="category-card">
          <div class="category-badge">Corte ♡</div>
          <div class="service-row" onclick="toggleItem('Corte femenino', 15000, this)">
            <div class="service-info"><div class="check-circle">✓</div><span class="service-name">Corte femenino</span></div>
            <div class="service-price">$15.000</div>
          </div>
          <div class="service-row" onclick="toggleItem('Corte + lavado', 18000, this)">
            <div class="service-info"><div class="check-circle">✓</div><span class="service-name">Corte + lavado</span></div>
            <div class="service-price">$18.000</div>
          </div>
          <div class="service-row" onclick="toggleItem('Corte flequillo', 8000, this)">
            <div class="service-info"><div class="check-circle">✓</div><span class="service-name">Corte flequillo</span></div>
            <div class="service-price">$8.000</div>
          </div>
          <div class="service-row" onclick="toggleItem('Corte puntas', 10000, this)">
            <div class="service-info"><div class="check-circle">✓</div><span class="service-name">Corte puntas</span></div>
            <div class="service-price">$10.000</div>
          </div>
        </div>

        <div class="note-version">Tu pelo, nuestra mejor versión ♡</div>
      </div>

      <!-- Barra de navegación inferior para Pantalla 2 -->
      <div id="bottomBar" class="bottom-bar" style="display: none;">
        <div class="selection-summary">
          <span id="summaryCount">0 seleccionados</span>
          <span id="summaryTotal" class="selection-total">$0</span>
        </div>
        <button class="btn-action-marsala" onclick="goTo('pantalla3')">
          SOLICITAR TURNO <span>→</span>
        </button>
      </div>

      <!-- PANTALLA 3: CONTACTO -->
      <div id="pantalla3" class="screen-content">
        <div class="header-bar" style="width: 100%;">
          <button class="btn-icon-back" onclick="goTo('pantalla2')">←</button>
          <div style="font-weight: 600; color: var(--marsala-primary); font-size: 16px;">Contacto</div>
          <div style="width: 38px;"></div>
        </div>

        <div>
          <div class="brand-title">LUMIÈRE</div>
          <div class="brand-subtitle">— Peluquería —</div>

          <div class="heart-divider"><span>♡</span></div>

          <div class="contact-badge">Mi contacto</div>

          <div class="phone-box">
            <span style="font-size: 22px; color: var(--marsala-primary);">✆</span>
            <span class="phone-text">2983351206</span>
            <button class="btn-copy-small" onclick="copyPhone()" title="Copiar número">📋</button>
          </div>

          <div class="heart-divider"><span>♡</span></div>

          <div class="hope-see-you">¡Te espero! ♡</div>
        </div>

        <div style="width: 100%;">
          <a id="btnWhatsapp" class="btn-whatsapp" href="https://wa.me/5492983351206?text=¡Hola%20Lumière%20Peluquería!%20Quisiera%20consultar%20por%20un%20turno." target="_blank">
            <span>💬</span> ENVIAR WHATSAPP
          </a>

          <div class="secondary-actions">
            <a class="btn-outlined-sec" href="tel:+5492983351206">
              <span>📞</span> Llamar
            </a>
            <button class="btn-outlined-sec" onclick="copyPhone()">
              <span>📋</span> Copiar
            </button>
          </div>

          <button class="btn-text-back" onclick="goTo('pantalla2')">
            ← Volver a la lista de precios
          </button>
        </div>
      </div>

    </div>
  </div>

  <script>
    let selectedServices = [];

    function goTo(screenId) {
      document.querySelectorAll('.screen-content').forEach(s => s.classList.remove('active'));
      const activeScreen = document.getElementById(screenId);
      if (activeScreen) activeScreen.classList.add('active');

      const bottomBar = document.getElementById('bottomBar');
      if (screenId === 'pantalla2') {
        bottomBar.style.display = 'flex';
      } else {
        bottomBar.style.display = 'none';
      }

      if (screenId === 'pantalla3') {
        updateWhatsAppLink();
      }
    }

    function toggleItem(name, price, element) {
      const idx = selectedServices.findIndex(s => s.name === name);
      if (idx >= 0) {
        selectedServices.splice(idx, 1);
        element.classList.remove('selected');
      } else {
        selectedServices.push({ name, price });
        element.classList.add('selected');
      }
      updateSummary();
    }

    function updateSummary() {
      const countEl = document.getElementById('summaryCount');
      const totalEl = document.getElementById('summaryTotal');
      const total = selectedServices.reduce((sum, s) => sum + s.price, 0);

      countEl.innerText = selectedServices.length + ' seleccionado(s)';
      totalEl.innerText = '$' + total.toLocaleString('es-AR');
    }

    function updateWhatsAppLink() {
      let msg = '¡Hola Lumière Peluquería! Quisiera consultar por un turno disponible.';
      if (selectedServices.length > 0) {
        const listStr = selectedServices.map(s => s.name + ' ($' + s.price.toLocaleString('es-AR') + ')').join(', ');
        const total = selectedServices.reduce((sum, s) => sum + s.price, 0);
        msg = '¡Hola Lumière Peluquería! Quisiera pedir un turno para: ' + listStr + '. Total estimado: $' + total.toLocaleString('es-AR') + '.';
      }
      document.getElementById('btnWhatsapp').href = 'https://wa.me/5492983351206?text=' + encodeURIComponent(msg);
    }

    function copyPhone() {
      navigator.clipboard.writeText('2983351206').then(() => {
        alert('Número copiado al portapapeles: 2983351206');
      }).catch(() => {
        alert('Teléfono: 2983351206');
      });
    }
  </script>
</body>
</html>
`;

const server = http.createServer((req, res) => {
  const url = req.url.split('?')[0];

  if (url === '/app-debug.apk') {
    if (fs.existsSync(APK_PATH)) {
      const stat = fs.statSync(APK_PATH);
      res.writeHead(200, {
        'Content-Type': 'application/vnd.android.package-archive',
        'Content-Length': stat.size,
        'Content-Disposition': 'attachment; filename="Lumiere-Peluqueria.apk"'
      });
      fs.createReadStream(APK_PATH).pipe(res);
    } else {
      res.writeHead(404, { 'Content-Type': 'text/plain' });
      res.end('APK not found');
    }
    return;
  }

  if (url === '/logo.jpg') {
    if (fs.existsSync(LOGO_PATH)) {
      const stat = fs.statSync(LOGO_PATH);
      res.writeHead(200, {
        'Content-Type': 'image/jpeg',
        'Content-Length': stat.size,
        'Cache-Control': 'public, max-age=86400'
      });
      fs.createReadStream(LOGO_PATH).pipe(res);
    } else {
      res.writeHead(404, { 'Content-Type': 'text/plain' });
      res.end('Image not found');
    }
    return;
  }

  if (url === '/' || url === '/index.html') {
    res.writeHead(200, {
      'Content-Type': 'text/html; charset=utf-8',
      'Cache-Control': 'no-cache'
    });
    res.end(HTML_CONTENT);
    return;
  }

  // Fallback to home
  res.writeHead(200, {
    'Content-Type': 'text/html; charset=utf-8'
  });
  res.end(HTML_CONTENT);
});

server.listen(PORT, '0.0.0.0', () => {
  console.log(`Lumiere Server running on port ${PORT}`);
});
