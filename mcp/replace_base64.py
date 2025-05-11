# -*- coding: utf-8 -*-
import os
import io
import re

SOURCE_DIR = "src/minecraft"

def replace_base64_usage(file_path):
    with io.open(file_path, 'r', encoding='utf-8') as f:
        content = f.read()

    modified = False

    # Remplace les imports
    if "sun.misc.BASE64Decoder" in content or "sun.misc.BASE64Encoder" in content:
        content = content.replace("import sun.misc.BASE64Decoder;", "import java.util.Base64;")
        content = content.replace("import sun.misc.BASE64Encoder;", "import java.util.Base64;")
        modified = True

    # Remplace les instanciations et appels
    decode_pattern = re.compile(r'new\s+BASE64Decoder\(\)\.decodeBuffer\s*\((.*?)\)', re.DOTALL)
    encode_pattern = re.compile(r'new\s+BASE64Encoder\(\)\.encode\s*\((.*?)\)', re.DOTALL)

    if decode_pattern.search(content):
        content = decode_pattern.sub(r'Base64.getDecoder().decode(\1)', content)
        modified = True

    if encode_pattern.search(content):
        content = encode_pattern.sub(r'Base64.getEncoder().encodeToString(\1)', content)
        modified = True

    if modified:
        print("[MODIFY] {}".format(file_path))
        with io.open(file_path + ".bak", 'w', encoding='utf-8') as f:
            f.write(content)
        with io.open(file_path, 'w', encoding='utf-8') as f:
            f.write(content)

def run():
    for root, _, files in os.walk(SOURCE_DIR):
        for file in files:
            if file.endswith(".java"):
                replace_base64_usage(os.path.join(root, file))

if __name__ == "__main__":
    run()
