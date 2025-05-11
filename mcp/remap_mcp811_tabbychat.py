# -*- coding: utf-8 -*-
import os
import re
import csv
import sys

SOURCE_DIR = os.path.join("src", "minecraft", "net", "ilexiconn")
FIELDS_CSV = "fields.csv"
METHODS_CSV = "methods.csv"
DRY_RUN = False  # Change to False to apply changes

def load_csv_mapping(filepath):
    mapping = {}
    with open(filepath, "r") as f:
        reader = csv.reader(f)
        for row in reader:
            if len(row) >= 3 and row[0] and row[1]:
                mapping[row[0]] = row[1]
    return mapping

def remap_file(filepath, field_map, method_map):
    with open(filepath, "r") as f:
        content = f.read()

    original_content = content
    for obf, name in field_map.iteritems():
        content = re.sub(r'\b%s\b' % re.escape(obf), name, content)
    for obf, name in method_map.iteritems():
        content = re.sub(r'\b%s\b' % re.escape(obf), name, content)

    if content != original_content:
        if DRY_RUN:
            print("[A FAIRE] %s" % filepath)
        else:
            with open(filepath, "w") as f:
                f.write(content)
            print("[MODIFIÉ] %s" % filepath)

def main():
    field_map = load_csv_mapping(FIELDS_CSV)
    method_map = load_csv_mapping(METHODS_CSV)

    java_files = []
    for root, dirs, files in os.walk(SOURCE_DIR):
        for file in files:
            if file.endswith(".java"):
                java_files.append(os.path.join(root, file))

    total = len(java_files)
    for i, path in enumerate(java_files):
        percent = int((i + 1) * 100.0 / total)
        sys.stdout.write("\r[PROGRESSION] %3d%% - %s" % (percent, os.path.basename(path)))
        sys.stdout.flush()
        remap_file(path, field_map, method_map)

    print("\n[FINI] %d fichiers traités." % total)

if __name__ == "__main__":
    main()
