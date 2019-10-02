#!/usr/bin/env python3

from antlr4 import *
from Listener import Listener
from TinyLexer import TinyLexer
from TinyParser import TinyParser
import sys


def main(argv):
    inp = FileStream(argv[1])
    lexer = TinyLexer(inp)

    token_stream = CommonTokenStream(lexer)
    parser = TinyParser(token_stream)

    listener = Listener()

    tree = parser.program()
    walker = ParseTreeWalker().walk(listener, tree)
    
    tables = listener.get_symbol_table()

    for t in tables:
        print("Symbol table %s" %  t.name)
        for k, v in t.symbols.items():
            if v[1]:
                print("name %s type %s value %s" % (k, v[0], v[1]))
            else:
                print("name %s type %s" % (k, v[0]))
        print("")


if __name__ == '__main__':
    main(sys.argv)
