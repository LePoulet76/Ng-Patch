# -*- coding: utf-8 -*-
import os
import io
import re

SOURCE_DIR = "src/minecraft"

# Regex pour repérer les imports de classes internes ($1, $State, etc.)
inner_import_pattern = re.compile(r'^import\s+[\w.]+\$\w+;', re.MULTILINE)

def clean_inner_imports(file_path):
    with io.open(file_path, 'r', encoding='utf-8') as f:
        content = f.read()

    if inner_import_pattern.search(content):
        cleaned = inner_import_pattern.sub('', content)

        print("[MODIFY] {}".format(file_path))
        with io.open(file_path + ".bak", 'w', encoding='utf-8') as f:
            f.write(content)

        with io.open(file_path, 'w', encoding='utf-8') as f:
            f.write(cleaned)

def run():
    for root, _, files in os.walk(SOURCE_DIR):
        for file in files:
            if file.endswith(".java"):
                clean_inner_imports(os.path.join(root, file))

if __name__ == "__main__":
    run()
