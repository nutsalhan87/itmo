from pyswip import Prolog
import re

prolog = Prolog()
prolog.consult('lab1.pl')

print("""С помощью этой программы вы получить ответы на некоторые вопросы из списка ниже:
1. 'Я хочу узнать информацию о герое %name%'
2. 'Я хочу узнать участников команды %name%'
3. 'Я хочу узнать суммарный урон команды героев %name%'
4. 'Я хочу узнать суммарный урон команды врагов %name%'
5. 'exit' - для выхода из программы
Запросы должны точь-в-точь соответствовать шаблону. Вводимые имена и названия чувствительны к регистру.
""")

commands = [\
    re.compile("\\A\\s*Я хочу узнать информацию о герое +(\\b.+\\b)"),\
    re.compile("\\A\\s*Я хочу узнать участников команды +(\\b.+\\b)"),\
    re.compile("\\A\\s*Я хочу узнать суммарный урон команды героев +(\\b.+\\b)"),\
    re.compile("\\A\\s*Я хочу узнать суммарный урон команды врагов +(\\b.+\\b)"),\
    re.compile("\\A\\s*exit\\s*"),\
]

while True:
    command = input()

    results = [pattern.search(command) for pattern in commands]
    if results[0] != None:
        name = results[0].group(1)
        res = prolog.query(f"hero('{name}', Race, Magictype, Level)")
        cnt = 0
        for r in res:
            cnt += 1
            print(f"{name} -- это {r['Race']} {r['Level']}-го уровня. У него {r['Magictype']} магия.")
        if cnt == 0:
            print('Героя с таким именем нет')
    elif results[1] != None:
        name = results[1].group(1)
        res = prolog.query(f"team('{name}', Members)")
        cnt = 0
        for r in res:
            cnt += 1
            print(f"В команде {name} состоят: {', '.join([str(member) for member in r['Members']])}")
        if cnt == 0:
            print('Такой команды нет')
    elif results[2] != None:
        name = results[2].group(1)
        res = prolog.query(f"team_spell('{name}', Attack)")
        cnt = 0
        for r in res:
            cnt += 1
            print(f"Суммарный магический урон команды {name} составляет {r['Attack']}")
        if cnt == 0:
            print('Такой команды нет')
    elif results[3] != None:
        name = results[3].group(1)
        res = prolog.query(f"team_attack('{name}', Attack)")
        cnt = 0
        for r in res:
            cnt += 1
            print(f"Суммарный физический урон команды {name} составляет {r['Attack']}")
        if cnt == 0:
            print('Такой команды нет')
    elif results[4] != None:
        break
    else:
        print('Такой команды нет')