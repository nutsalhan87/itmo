% hero(Name, Race, Magictype, Level) - факт о герое с именем Name, расой Race, типом магии Magictype и уровнем Level
hero(tom, human, active, 3).
hero(dom, human, static, 7).
hero(lom, elf, active, 17).
hero(kom, elf, static, 18).
hero(rom, human, active, 18).

% spell(Race, Magictype, Attack_point) - Attack_point - количество урона, который нанесет герой расы Race с типом магии Magictype
spell(human, active, 5).
spell(human, static, 3).
spell(elf, active, 2).
spell(elf, static, 6).

% enemy(ID, Type, Level) - факт о враге типа Type и с уровнем Level. Т.к. у них не может быть имени, они нумеруются с помощью ID для идентификации
enemy(1, orc, 3).
enemy(2, orc, 7).
enemy(3, megawolf, 7).
enemy(4, megawolf, 11).

% attack(Type, Attack_point) - Attack_point - количество урона, который нанесет враг типа Type
attack(orc, 1).
attack(megawolf, 7).

% team(Team_name, Member_identifiers) - команда героев или врагов с названием Team_name
team('древние русы', [tom, dom, lom, kom, rom]).
team('ящеры', [1, 2, 3, 4]).

team_array_spell([], 0).
team_array_spell([H|T], Attack):-
    team_array_spell(T, A1),
    hero(H, Race, Magictype, Level),
    spell(Race, Magictype, Attack_point), !,
    Attack is A1 + (Level * Attack_point).
team_spell(Name, Attack):- team(Name, Team), team_array_spell(Team, Attack). % получение суммарного урона, который нанесет команда героев с именем Name 

team_array_attack([], 0).
team_array_attack([H|T], Attack):-
    team_array_attack(T, A1),
    enemy(H, Type, Level),
    attack(Type, Attack_point), !,
    Attack is A1 + (Level * Attack_point).
team_attack(Name, Attack):- team(Name, Team), team_array_attack(Team, Attack). % получение суммарного урона, который нанесет команда врагов с именем Name 

% победит ли команда героев Heroes_name команду врагов Enemies_name
will_heroes_win_enemies(Heroes_name, Enemies_name):-
    team_spell(Heroes_name, Heroes_attack),
    team_attack(Enemies_name, Enemies_attack),
    Heroes_attack > Enemies_attack.

print_hero_info(Hero_name):- 
    hero(Hero_name, Race, Magictype, Level),
    writef('Hero is %t with name %t. Hero uses %t magic and has %t level.\n', [Race, Hero_name, Magictype, Level]).
print_enemy_info(ID):-
    enemy(ID, Type, Level),
    writef('Enemy is %t with %t level and its ID is %t.\n', [Type, Level, ID]).

/*
findall(Name, hero(Name, human, _, _), Team), team_array_spell(Team, Attack).
findall(Name, hero(Name, elf, _, _), Team), team_array_spell(Team, Attack).

findall(Name, (hero(Name, _, _, Lvl), Lvl > 5, Lvl < 18), Team), team_array_spell(Team, Attack).

write('Elfs\' info:\n'), findall(Name, hero(Name, elf, _, _), Elfs), maplist(print_hero_info, Elfs).

findall(Name, hero(Name, human, _, _), Team), team_array_spell(Team, Spell), team_attack(ящеры, Attack), ((Spell > Attack) -> write('Humans won.\n'); write('Enemies won.\n')).
*/