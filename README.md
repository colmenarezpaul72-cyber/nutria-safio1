# Nutria Mods (Fabric 1.21.1)

Silla de ruedas · 11 bicicletas (la roja es para 2) · Autobús volador con música · Tablet con voz de Loquendo · Letreros con `/nutria`.
Todo está en la pestaña creativa **Nutria Mods**.

## Cómo obtener el .jar (GitHub)
1. Crea un repositorio y sube TODO el contenido de esta carpeta (incluida `.github`).
2. Pestaña **Actions** → corre **build** → descarga el artifact `nutria-mods-jar` → adentro está `nutria-mods-3.0.0.jar`.
3. Ponlo en `mods/` (cliente Y servidor). Requiere Fabric API.

## Comandos
- `/nutria letrero` → MODO LETRERO: todo lo que escribas en el chat NO se envía y se vuelve un letrero (brillante). Sigue activo hasta que pongas `/nutria letrero parar`.
- `/nutria menu` → menú: elige tipo de letrero (11 maderas) y tinte (11 colores) y pulsa la esmeralda.

## Bicicletas
Items `bici_<color>` (11 colores). Click derecho en el suelo la pone; click derecho para subir; agachado + click derecho (vacía) la guardas. La roja lleva 2 jugadores.

## Tablet (voz)
Pon tu API key de https://fish.audio/app/api-keys en `config/silla_tablet.json` (del servidor) y reinicia. Click derecho con la tablet → escribe → Enter.

## Ajustes si algo se ve raro
- Altura del jugador en la silla: `SEAT_ATTACH` en `WheelchairEntity.java`.
- Altura en la bici: los dos `Vec3d` en `ModEntities.java` (BIKE).
- Los modelos se regeneran con `python3 tools/gen_all.py`.

## Autobús volador
Item `autobus` (pestaña Nutria Mods). Click derecho en el suelo lo pone; click derecho para subir (12 asientos: conductor a la izquierda, acompañante, y 5 sofás dobles).
El conductor maneja con WASD. **Tecla B** (se cambia en Controles → Nutria Mods) abre el panel: música (Dulce azar), detener música y **Volar**.
Volando: W avanza hacia donde miras (mira arriba = sube), sin W se queda flotando. Sin daño por caída. Agachado + click derecho (vacío) lo guarda.

## Música (Dulce azar)
Por derechos de autor NO incluyo la canción. Pon tu propio archivo en
`src/main/resources/assets/silla/sounds/dulce_azar.ogg` (OGG Vorbis **mono**) antes de compilar,
o dentro del .jar en `assets/silla/sounds/`.
