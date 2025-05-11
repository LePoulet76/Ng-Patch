# -*- coding: utf-8 -*-
import os
import re

SRC_DIR = os.path.join("src", "minecraft")

def fix_inner_class_references(filepath):
    with open(filepath, 'r') as f:
        content = f.read()

    # Remplacer les classes internes du type Back.1 par Back_1
    content_new = re.sub(r'(\b\w+)\.(\d+)\b', r'\1_\2', content)

    # Remplacer les noms de classes internes typiques comme Timeline$Modes → Timeline_Modes
    content_new = re.sub(r'(\b\w+)\$(\w+)\b', r'\1_\2', content_new)

    if content != content_new:
        with open(filepath, 'w') as f:
            f.write(content_new)
        print("[MODIFIÉ   ]", filepath)

def main():
    print("=== CORRECTION DES RÉFÉRENCES INTERNES ===")
    for root, dirs, files in os.walk(SRC_DIR):
        for file in files:
            if file.endswith(".java"):
                fix_inner_class_references(os.path.join(root, file))
    print("=== TERMINÉ ===")

if __name__ == "__main__":
    main()
