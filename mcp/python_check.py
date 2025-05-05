import os
import re

project_dir = "src/minecraft"
import_pattern = re.compile(r"^\s*import\s+([\w\.]+)")

all_imports = {}

for root, _, files in os.walk(project_dir):
    for file in files:
        if file.endswith(".java"):
            path = os.path.join(root, file)
            try:
                f = open(path, "r")
                lines = f.readlines()
                f.close()
            except:
                continue
            for line in lines:
                match = import_pattern.match(line)
                if match:
                    imported = match.group(1)
                    if not all_imports.get(imported):
                        all_imports[imported] = []
                    all_imports[imported].append(path)

print("[*] Total imports found: %d" % len(all_imports))
print("")

missing = []
for imp in sorted(all_imports.keys()):
    parts = imp.split(".")
    if parts[0] in ["java", "javax", "net", "org", "com"]:
        continue
    found = False
    for root, _, files in os.walk(project_dir):
        for file in files:
            if file.endswith(".java") and file[:-5] == parts[-1]:
                found = True
                break
        if found:
            break
    if not found:
        missing.append(imp)

if not missing:
    print("[OK] All imports seem to be present or external.")
else:
    print("[!!] Missing or unresolved imports:")
    for m in missing:
        print("  - %s" % m)
