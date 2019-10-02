#!/usr/bin/env python3

from antlr4 import *
from Listener import Listener
from TinyLexer import TinyLexer
from TinyParser import TinyParser

import sys


def convert(string):
    data = [string, None]
    s = string.split(" ")
    if s[0][:-1] in ["add", "mul"]:
        data[0] = " ".join(s[:-1])
        data[1] = "move %s %s" % (s[2], s[3])
    elif s[0][:-1] in ["sub", "div"]:
        data[0] = s[0] + " " + s[2] + " " + s[1]
        data[1] = "move %s %s" % (s[1], s[3])
    return data


def main(argv):
    inp = FileStream(argv[1])
    lexer = TinyLexer(inp)

    token_stream = CommonTokenStream(lexer)
    parser = TinyParser(token_stream)

    listener = Listener()

    tree = parser.program()
    walker = ParseTreeWalker().walk(listener, tree)

    lines = []
    assembly_code = listener.assembly_code
    for i in range(len(assembly_code)):
        while assembly_code[i]:
            line = assembly_code[i].pop()
            l = convert(line)
            assert isinstance(l, list)
            assert len(l) == 2
            lines.append(l[0])
            if l[1]:
                lines.append(l[1])
    for xyz in lines:
        print(xyz)
    print("sys halt")


if __name__ == '__main__':
    main(sys.argv)
