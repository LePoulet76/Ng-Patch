# -*- coding: utf-8 -*-
import os
import re
import io

SOURCE_DIR = "src/minecraft"  # adapte si nécessaire

method_pattern = re.compile(r'(public|protected|private)\s+[\w<>]+\s+(\w+)\s*\(([^)]*)\)\s*\{', re.MULTILINE)

def clean_duplicates(file_path):
    with io.open(file_path, 'r', encoding='utf-8') as f:
        content = f.read()

    matches = list(method_pattern.finditer(content))
    seen = set()
    removals = []

    for match in matches:
        method_name = match.group(2)
        method_args = match.group(3).strip()
        method_signature = method_name + "(" + method_args + ")"

        if method_signature in seen:
            start = match.start()
            brace_count = 0
            end = start
            while end < len(content):
                if content[end] == '{':
                    brace_count += 1
                elif content[end] == '}':
                    brace_count -= 1
                    if brace_count == 0:
                        break
                end += 1
            removals.append((start, end + 1))
        else:
            seen.add(method_signature)

    if removals:
        print("[MODIFY] {} - {} duplicates removed.".format(file_path, len(removals)))
        with io.open(file_path + ".bak", 'w', encoding='utf-8') as f:
            f.write(content)

        for start, end in sorted(removals, reverse=True):
            content = content[:start] + u"\n" + content[end:]

        with io.open(file_path, 'w', encoding='utf-8') as f:
            f.write(content)

def run_cleanup():
    for root, _, files in os.walk(SOURCE_DIR):
        for file in files:
            if file.endswith(".java"):
                clean_duplicates(os.path.join(root, file))

if __name__ == "__main__":
    run_cleanup()
