# -*- coding: utf-8 -*-
import os
import zipfile

# Dossiers standards de MCP où il cherche les libs
classpath_dirs = [
    "jars/versions/1.6.4/1.6.4.jar",
    "lib",
    "jars/libraries"
]

def list_all_jars(base_dirs):
    jars = []
    for base in base_dirs:
        if os.path.isfile(base) and base.endswith(".jar"):
            jars.append(base)
        elif os.path.isdir(base):
            for root, dirs, files in os.walk(base):
                for file in files:
                    if file.endswith(".jar"):
                        jars.append(os.path.join(root, file))
    return jars

print "== JARs utilisés par MCP =="
jars = list_all_jars(classpath_dirs)
for jar in jars:
    print "  -> " + jar
print "\nTotal : %d jars trouvés\n" % len(jars)

# Exemple : tester si une classe importante est présente
to_find = "fr/nationsglory/modelapi/ModelOBJ.class"
found = False
for jar in jars:
    try:
        zipf = zipfile.ZipFile(jar, 'r')
        if to_find in zipf.namelist():
            print "✅ Trouvé dans : " + jar
            found = True
        zipf.close()
    except Exception as e:
        pass

if not found:
    print "❌ Classe '%s' non trouvée dans les JARs." % to_find
