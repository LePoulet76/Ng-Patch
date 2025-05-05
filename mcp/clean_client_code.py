# -*- coding: utf-8 -*-
import os
import re

# Liste des patterns liés au client à exclure
client_patterns = [
    r'^import\s+net\.minecraft\.client\..*',
    r'^import\s+fr\.nationsglory\.remoteitem\.client\..*',
    r'^import\s+net\.ilexiconn\.nationsgui\.forge\.client\..*',
    r'^import\s+org\.bukkit\..*',
    r'^import\s+universalelectricity\..*',
    r'^import\s+icbm\..*',
    r'^import\s+micdoodle8\..*',
    r'^import\s+co\.uk\.flansmods\..*',
]

# Dossier à nettoyer
SRC_DIR = os.path.join("src", "minecraft_server")

def clean_file(file_path):
    try:
        with open(file_path, 'r') as f:
            lines = f.readlines()
    except:
        print("Erreur de lecture : " + file_path)
        return

    cleaned_lines = []
    skip_file = False

    for line in lines:
        stripped = line.strip()
        for pattern in client_patterns:
            if re.match(pattern, stripped):
                skip_file = True
                break
        if not skip_file:
            cleaned_lines.append(line)

    if skip_file:
        print("Fichier contenant des imports client (à vérifier/supprimer) : " + file_path)
    else:
        with open(file_path, 'w') as f:
            f.writelines(cleaned_lines)
        print("Nettoyé : " + file_path)

def walk_and_clean():
    for root, dirs, files in os.walk(SRC_DIR):
        for fname in files:
            if fname.endswith(".java"):
                fpath = os.path.join(root, fname)
                clean_file(fpath)

if __name__ == "__main__":
    walk_and_clean()
