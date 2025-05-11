# -*- coding: utf-8 -*-
import os
import io

SOURCE_DIR = "src/minecraft"

def replace_linkedtreemap(file_path):
    with io.open(file_path, 'r', encoding='utf-8') as f:
        content = f.read()

    if "com.google.gson.internal.LinkedTreeMap" in content:
        content = content.replace(
            "import com.google.gson.internal.LinkedTreeMap;",
            "import java.util.LinkedHashMap;"
        )
        content = content.replace("LinkedTreeMap<", "LinkedHashMap<")
        content = content.replace("new LinkedTreeMap()", "new LinkedHashMap()")

        print("[MODIFY] {}".format(file_path))
        with io.open(file_path + ".bak", 'w', encoding='utf-8') as f:
            f.write(content)

        with io.open(file_path, 'w', encoding='utf-8') as f:
            f.write(content)

def run():
    for root, _, files in os.walk(SOURCE_DIR):
        for file in files:
            if file.endswith(".java"):
                replace_linkedtreemap(os.path.join(root, file))

if __name__ == "__main__":
    run()
