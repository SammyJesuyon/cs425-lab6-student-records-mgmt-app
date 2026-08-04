#!/usr/bin/env python3
"""Render captured terminal transcripts as readable PNG evidence images."""

from pathlib import Path
import textwrap

from PIL import Image, ImageDraw, ImageFont


PROJECT_DIR = Path(__file__).resolve().parents[1]
EVIDENCE_DIR = PROJECT_DIR / "evidence"
FONT_PATH = "/System/Library/Fonts/Menlo.ttc"


def render(transcript_path: Path) -> None:
    raw_lines = transcript_path.read_text(encoding="utf-8").splitlines()
    lines = []
    for raw in raw_lines:
        wrapped = textwrap.wrap(
            raw,
            width=112,
            replace_whitespace=False,
            drop_whitespace=False,
        )
        lines.extend(wrapped or [""])

    width = 1600
    line_height = 30
    title_height = 64
    horizontal_padding = 34
    vertical_padding = 28
    height = title_height + vertical_padding * 2 + line_height * len(lines)

    image = Image.new("RGB", (width, height), "#111827")
    draw = ImageDraw.Draw(image)
    title_font = ImageFont.truetype(FONT_PATH, 22)
    text_font = ImageFont.truetype(FONT_PATH, 21)

    draw.rectangle((0, 0, width, title_height), fill="#202938")
    for index, color in enumerate(("#FF5F57", "#FEBC2E", "#28C840")):
        x = 30 + index * 38
        draw.ellipse((x, 21, x + 20, 41), fill=color)
    draw.text(
        (width / 2, title_height / 2),
        f"Terminal - {transcript_path.name}",
        font=title_font,
        fill="#D1D5DB",
        anchor="mm",
    )

    y = title_height + vertical_padding
    for line in lines:
        color = "#A7F3D0" if line.startswith("$") else "#F3F4F6"
        if line.startswith("PASS") or line.startswith("All "):
            color = "#86EFAC"
        draw.text((horizontal_padding, y), line, font=text_font, fill=color)
        y += line_height

    output_path = transcript_path.with_suffix(".png")
    image.save(output_path, "PNG", optimize=True)
    print(output_path)


def main() -> None:
    for transcript_path in sorted(EVIDENCE_DIR.glob("*.txt")):
        render(transcript_path)


if __name__ == "__main__":
    main()
