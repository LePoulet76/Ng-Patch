# -*- coding: utf-8 -*-
import os
import csv
import re
import sys

SOURCE_DIR = 'src/minecraft'

# Détection du mode dry-run
DRY_RUN = '--dry-run' in sys.argv

def load_mapping(csv_file, obf_index, deobf_index):
    mapping = {}
    with open(csv_file, 'rb') as f:
        reader = csv.reader(f)
        for row in reader:
            if len(row) > max(obf_index, deobf_index):
                obf = row[obf_index].strip()
                deobf = row[deobf_index].strip()
                if obf and deobf and obf != deobf:
                    mapping[obf] = deobf
    return mapping

def apply_remapping_to_file(filepath, field_map, method_map):
    with open(filepath, 'rb') as f:
        content = f.read()

    original = content

    for obf, name in field_map.items():
        content = re.sub(r'\b%s\b' % re.escape(obf), name, content)

    for obf, name in method_map.items():
        content = re.sub(r'\b%s\b' % re.escape(obf), name, content)

    if content != original:
        if DRY_RUN:
            print("[DRY-RUN] %s" % filepath.replace('/', os.sep))
        else:
            with open(filepath, 'wb') as f:
                f.write(content)
            print("[MODIFIÉ] %s" % filepath.replace('/', os.sep))

def collect_java_files(source_dir):
    java_files = []
    for root, dirs, files in os.walk(source_dir):
        for fname in files:
            if fname.endswith('.java'):
                java_files.append(os.path.join(root, fname))
    return java_files

def walk_and_remap(source_dir, field_map, method_map):
    java_files = collect_java_files(source_dir)
    total = len(java_files)

    for i, path in enumerate(java_files):
        progress = int((i + 1) * 100 / total)
        sys.stdout.write("\r[PROGRESSION] %3d%% - %s" % (progress, os.path.basename(path)))
        sys.stdout.flush()
        apply_remapping_to_file(path, field_map, method_map)

    print("\n[FINI] %d fichiers traités." % total)

def main():
    field_map = load_mapping('fields.csv', 0, 1)
    method_map = load_mapping('methods.csv', 0, 1)
    walk_and_remap(SOURCE_DIR, field_map, method_map)

if __name__ == '__main__':
    main()
