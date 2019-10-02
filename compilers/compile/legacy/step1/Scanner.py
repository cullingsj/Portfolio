#!/usr/bin/env python3

import sys
from antlr4 import *
from TinyLexer import TinyLexer
from TinyParser import TinyParser


def main(argv):
    inp = FileStream(argv[1])
    lexer = TinyLexer(inp)
    
    '''
    tokens = lexer.getAllTokens()

    types = {}
    t = open("TinyLexer.tokens", "r")
    for line in t:
        line = line.replace("\n", "")
        line = line.replace("\r", "")
        l = line.split("=")
        types[int(l[1])] = l[0]
    t.close()

    #f = open("result.txt", "w")
    for token in tokens:
        print(types[token.type], token.text)
    #    f.write("Token Type: %s\r\n" % types[token.type])
    #    f.write("Value: %s\r\n" % token.text)
    #f.close()
    '''

    stream = CommonTokenStream(lexer)
    parser = TinyParser(stream)
    tree = parser.program()
    print(tree.toStringTree(recog=parser))
    print(parser.getNumberOfSyntaxErrors())
    
if __name__ == '__main__':
    main(sys.argv)
