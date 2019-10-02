


def convert(lines):
    flagOpers = ['addi', 'addr', 'muli', 'mulr']
    for index in range(len(lines)):
        adjust = [True for oper in flagOpers if oper in lines[index]]
        if 'subi' in lines[index] or 'subr' in lines[index]:
            line = lines[index].split(' ')
            tmp1 = line[1]
            tmp2 = line[2]
            line[1] = tmp2
            line[2] = tmp1

            new_line = '\nmove %s %s' % (line[2], line[3])
            x = line[:-1]
            new_line = ' '.join(y for y in x) + new_line
            lines[index] = new_line
            lines[index] = new_line
        if 'divi' in lines[index] or 'divr' in lines[index]:
            line = lines[index].split(" ")
            nom = line[1]
            denom = line[2]
            res = line[3]
            x = line[:-1]
            x[2] = nom
            x[1] = denom
            new_line = '\nmove %s %s' % (nom,res)
            new_line = ' '.join(y for y in x) + new_line
            lines[index] = new_line
        if True in adjust:
            line = lines[index].split(" ")
            x = line[:-1]
            new_line = '\nmove %s %s' % (x[-1], line[-1])
            new_line = ' '.join(y for y in x) + new_line
            lines[index] = new_line
    for l in lines:
        print(l)


