"""Generador del PDF de entrega de la Actividad 5 - POO 2026-1.

Aplicacion de Interfaz Grafica (Swing) con un formulario y botones Create, Read,
Update y Delete (CRUD) que operan sobre un archivo de texto. El PDF contiene:
portada, descripcion, captura de la interfaz, diagrama de clases, diagrama de
casos de uso, codigo fuente y enlaces de GitHub.
"""
import os
from reportlab.lib.pagesizes import letter
from reportlab.lib.styles import getSampleStyleSheet, ParagraphStyle
from reportlab.lib.units import inch, cm
from reportlab.lib import colors
from reportlab.platypus import (
    SimpleDocTemplate, Paragraph, Spacer, PageBreak, HRFlowable, Image, XPreformatted
)
from reportlab.lib.enums import TA_CENTER, TA_JUSTIFY

BASE = os.path.dirname(os.path.abspath(__file__))
OUTPUT = os.path.join(BASE, "Actividad5_IvanGomez_JuanBuiles.pdf")
DIAGRAMAS = os.path.join(BASE, "diagramas")
REPO_BASE = "https://github.com/ivannk12/poo-actividad5/blob/main"

doc = SimpleDocTemplate(OUTPUT, pagesize=letter, rightMargin=2.5 * cm,
                        leftMargin=2.5 * cm, topMargin=2.5 * cm, bottomMargin=2.5 * cm)

styles = getSampleStyleSheet()
titulo_uni = ParagraphStyle("titulo_uni", parent=styles["Normal"], fontSize=16,
                            fontName="Helvetica-Bold", alignment=TA_CENTER, spaceAfter=6)
titulo_materia = ParagraphStyle("titulo_materia", parent=styles["Normal"], fontSize=13,
                                fontName="Helvetica-Bold", alignment=TA_CENTER, spaceAfter=4)
subtitulo = ParagraphStyle("subtitulo", parent=styles["Normal"], fontSize=11,
                           fontName="Helvetica", alignment=TA_CENTER, spaceAfter=4)
campo_portada = ParagraphStyle("campo_portada", parent=styles["Normal"], fontSize=11,
                               fontName="Helvetica", alignment=TA_CENTER, spaceAfter=6)
seccion = ParagraphStyle("seccion", parent=styles["Normal"], fontSize=12,
                         fontName="Helvetica-Bold", textColor=colors.HexColor("#8B0000"),
                         spaceBefore=12, spaceAfter=6)
label = ParagraphStyle("label", parent=styles["Normal"], fontSize=10,
                       fontName="Helvetica-Bold", spaceAfter=2, spaceBefore=6)
texto = ParagraphStyle("texto", parent=styles["Normal"], fontSize=10,
                       fontName="Helvetica", alignment=TA_JUSTIFY, spaceAfter=8)
codigo_style = ParagraphStyle("codigo_style", parent=styles["Normal"], fontSize=7.2,
                              fontName="Courier", leading=9.5, spaceAfter=4,
                              leftIndent=8, rightIndent=8,
                              backColor=colors.HexColor("#F0F0F0"),
                              borderColor=colors.HexColor("#CCCCCC"),
                              borderWidth=0.5, borderPadding=6)
url_texto = ParagraphStyle("url_texto", parent=styles["Normal"], fontSize=8.5,
                           fontName="Courier", textColor=colors.HexColor("#0000CC"), spaceAfter=4)


def code_block(lines):
    text = "\n".join(l.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;") for l in lines)
    return XPreformatted(text, codigo_style)


def imagen(filename, max_w_cm=15, max_h_cm=17):
    path = os.path.join(DIAGRAMAS, filename)
    from PIL import Image as PilImage
    with PilImage.open(path) as im:
        w_px, h_px = im.size
    ratio = w_px / h_px
    w = max_w_cm * cm
    h = w / ratio
    if h > max_h_cm * cm:
        h = max_h_cm * cm
        w = h * ratio
    img = Image(path, width=w, height=h)
    img.hAlign = "CENTER"
    return img


def leer_codigo(rel_path):
    with open(os.path.join(BASE, rel_path)) as f:
        return [l.rstrip("\n") for l in f.readlines()]


story = []

# ---- PORTADA ----
story.append(Spacer(1, 1.6 * inch))
story.append(Paragraph("UNIVERSIDAD NACIONAL DE COLOMBIA", titulo_uni))
story.append(Paragraph("Facultad de Minas - Sede Medellin", subtitulo))
story.append(Spacer(1, 0.4 * inch))
story.append(HRFlowable(width="80%", thickness=1, color=colors.HexColor("#8B0000"), hAlign="CENTER"))
story.append(Spacer(1, 0.3 * inch))
story.append(Paragraph("Programacion Orientada a Objetos 2026-1", titulo_materia))
story.append(Spacer(1, 0.15 * inch))
story.append(Paragraph("Actividad 5 - En equipo (20%)", subtitulo))
story.append(Paragraph("Aplicacion de Interfaz Grafica con CRUD sobre archivo", subtitulo))
story.append(Spacer(1, 0.5 * inch))
story.append(Paragraph("<b>Docente:</b> Walter Hugo Arboleda Mazo", campo_portada))
story.append(Spacer(1, 0.15 * inch))
story.append(Paragraph("<b>Integrantes:</b>", campo_portada))
story.append(Paragraph("Ivan Camilo Gomez Gallego - 1092853135", campo_portada))
story.append(Paragraph("Juan Andres Builes Arenas - 1021924317", campo_portada))
story.append(Spacer(1, 0.3 * inch))
story.append(Paragraph("<b>Fecha de entrega:</b> Jueves 9 de julio de 2026", campo_portada))
story.append(Spacer(1, 0.3 * inch))
story.append(Paragraph("<b>Repositorio GitHub:</b>", campo_portada))
story.append(Paragraph("https://github.com/ivannk12/poo-actividad5", campo_portada))
story.append(PageBreak())

# ---- DESCRIPCION ----
story.append(Paragraph("Descripcion de la aplicacion", seccion))
story.append(HRFlowable(width="100%", thickness=0.5, color=colors.HexColor("#CCCCCC")))
story.append(Paragraph("Enunciado:", label))
story.append(Paragraph(
    "Aplicacion de Interfaz Grafica de Usuario (Swing) que implementa un formulario con los "
    "botones Create, Read, Update y Delete (CRUD). La informacion se persiste en un archivo de "
    "texto (estudiantes.txt), siguiendo el manejo de archivos en Java (FileWriter, PrintWriter, "
    "FileReader y BufferedReader). Como caso de uso se administra un registro de estudiantes, "
    "donde cada estudiante posee un identificador, un nombre, un programa academico y una edad.",
    texto))
story.append(Paragraph("Operaciones CRUD implementadas:", label))
story.append(Paragraph(
    "&bull; <b>Create (Crear):</b> agrega un nuevo estudiante al final del archivo.<br/>"
    "&bull; <b>Read (Leer):</b> lee todos los estudiantes del archivo y los muestra en una tabla.<br/>"
    "&bull; <b>Update (Actualizar):</b> modifica los datos de un estudiante identificado por su ID.<br/>"
    "&bull; <b>Delete (Eliminar):</b> elimina del archivo el estudiante identificado por su ID.",
    texto))
story.append(Paragraph("Arquitectura de clases:", label))
story.append(Paragraph(
    "&bull; <b>Estudiante:</b> modelo de datos; convierte el objeto a/desde una linea de texto.<br/>"
    "&bull; <b>GestorArchivo:</b> concentra la logica CRUD de lectura y escritura del archivo.<br/>"
    "&bull; <b>VentanaPrincipal:</b> interfaz grafica (formulario, botones y tabla de resultados).<br/>"
    "&bull; <b>Principal:</b> punto de entrada de la aplicacion.",
    texto))
story.append(Paragraph("Material guia: file-handling in Java with CRUD operations (GeeksforGeeks).", texto))

story.append(Paragraph("Interfaz grafica de usuario:", label))
story.append(imagen("gui_crud.png", max_w_cm=15, max_h_cm=12))
story.append(PageBreak())

# ---- DIAGRAMAS ----
story.append(Paragraph("Diagrama de clases (UML)", seccion))
story.append(HRFlowable(width="100%", thickness=0.5, color=colors.HexColor("#CCCCCC")))
story.append(Spacer(1, 6))
story.append(imagen("clases.png", max_w_cm=15, max_h_cm=20))
story.append(PageBreak())

story.append(Paragraph("Diagrama de casos de uso (UML)", seccion))
story.append(HRFlowable(width="100%", thickness=0.5, color=colors.HexColor("#CCCCCC")))
story.append(Spacer(1, 6))
story.append(imagen("casos_uso.png", max_w_cm=16, max_h_cm=12))
story.append(PageBreak())

# ---- CODIGO FUENTE ----
story.append(Paragraph("Codigo fuente (Java)", seccion))
story.append(HRFlowable(width="100%", thickness=0.5, color=colors.HexColor("#CCCCCC")))
for archivo in ["Estudiante.java", "GestorArchivo.java", "VentanaPrincipal.java", "Principal.java"]:
    story.append(Paragraph(archivo, label))
    story.append(code_block(leer_codigo(f"app/{archivo}")))
    story.append(Paragraph(f"{REPO_BASE}/app/{archivo}", url_texto))
    story.append(Spacer(1, 8))

doc.build(story)
print("PDF generado:", OUTPUT)
