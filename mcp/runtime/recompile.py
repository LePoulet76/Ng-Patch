# -*- coding: utf-8 -*-
import sys
import logging
import glob
import os
from optparse import OptionParser

from commands import Commands, CLIENT, SERVER, CalledProcessError
from mcp import recompile_side

def main():
    parser = OptionParser(version='MCP %s' % Commands.fullversion())
    parser.add_option('--client', dest='only_client', action='store_true', help='only process client', default=False)
    parser.add_option('--server', dest='only_server', action='store_true', help='only process server', default=False)
    parser.add_option('-c', '--config', dest='config', help='additional configuration file')
    parser.add_option('--extra-classpath', dest='extra_cp', help='extra classpath to inject', default='')
    options, _ = parser.parse_args()
    extra_jars = glob.glob(os.path.join("lib", "*.jar"))
    if extra_jars:
        joined_jars = os.pathsep.join(extra_jars)
        if options.extra_cp:
            options.extra_cp += os.pathsep + joined_jars
        else:
            options.extra_cp = joined_jars
    recompile(options)

def recompile(options):
    errorcode = 0
    try:
        commands = Commands(options.config, verify=True)

        # Ajouter extra_cp si défini
        if hasattr(options, 'extra_cp') and options.extra_cp:
            print("[DEBUG] Extra classpath injecté : %s" % options.extra_cp)
            commands.extra_classpath = options.extra_cp  # nécessite que Commands accepte cet attribut

        # client or server
        process_client = not options.only_server
        process_server = not options.only_client

        if process_client:
            try:
                recompile_side(commands, CLIENT)
            except CalledProcessError:
                errorcode = 2
        if process_server:
            try:
                recompile_side(commands, SERVER)
            except CalledProcessError:
                errorcode = 3
    except Exception:
        logging.exception('FATAL ERROR')
        sys.exit(1)
    sys.exit(errorcode)

if __name__ == '__main__':
    main()
