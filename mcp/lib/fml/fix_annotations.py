# -*- coding: utf-8 -*-
import os
import io
import re

def fix_annotations(directory):
    """Supprime les annotations @SideOnly en double dans TOUS les fichiers .java, y compris dans les sous-dossiers récursifs"""
    annotation_pattern = re.compile(r'@SideOnly\(Side\.(CLIENT|SERVER)\)')
    
    file_count = 0

    print("[DEBUG] Démarrage du nettoyage des annotations...")

    for root, _, files in os.walk(directory):
        for file in files:
            if file.endswith(".java"):
                file_path = os.path.join(root, file)

                # Lire le fichier
                with io.open(file_path, 'r', encoding="utf-8") as file:
                    lines = file.readlines()

                # Nettoyer les annotations dupliquées
                cleaned_lines = []
                seen_annotations = set()
                
                for line in lines:
                    match = annotation_pattern.search(line)
                    if match:
                        annotation = match.group(0)
                        if annotation in seen_annotations:
                            continue  # Ignore les annotations en double
                        seen_annotations.add(annotation)

                    cleaned_lines.append(line)

                # Réécrire le fichier
                with io.open(file_path, 'w', encoding="utf-8") as file:
                    file.writelines(cleaned_lines)

                file_count += 1

    print("[DEBUG] Nettoyage terminé : {} fichiers Java modifiés.".format(file_count))

# Exécuter automatiquement si le script est lancé seul
if __name__ == "__main__":
    fix_annotations(r"C:\forge\mcp\src\minecraft")
