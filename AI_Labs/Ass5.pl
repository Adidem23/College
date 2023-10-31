% Facts: Define relationships in the family tree
male(kerba).
male(machindra).
male(suraj).
male(baburao).
male(udhav).
male(prakash).
male(mahesh).
male(aditya).
male(atharv).
male(om).
male(nagesh).
male(daji).
male(kiran).
male(navnath).
male(adinath).
female(kashibai).
female(shamal).
female(kusum).
female(balu).
female(shalu).
female(nita).
female(lalita).
female(balika).
female(anjali).
female(surykala).
female(prerna).
female(jyoti).
female(kavita).


husband(machindra,kashibai).
husband(suaraj,shamal).
husband(baburao,kusum).
husband(kiran,balu).
husband(navnath,shalu).
husband(adinath, nita).
husband(nagesh, lalita).
husband(udhav,balika).
husband(prakash,anjali).
husband(daji, surykala).

parent(kerba, machindra).
parent(kerba, suraj).
parent(kerba, baburao).
parent(machindra, lalita).
parent(machindra, udhav).
parent(machindra, prakash).
parent(machindra, surykala).
parent(kashibai, lalita).
parent(kashibai, udhav).
parent(kashibai, prakash).
parent(kashibai, surykala).
parent(suraj, kiran).
parent(suraj, navnath).
parent(shamal, kiran).
parent(shamal, navnath).
parent(baburao, adinath).
parent(kusum, adinath).
parent(lalita, mahesh).
parent(nagesh, mahesh).
parent(udhav, aditya).
parent(udhav, atharv).
parent(balika, aditya).
parent(balika, atharv).
parent(praksh, om).
parent(prakash, prerna).
parent(anjali, om).
parent(anjali, prerna).
parent(surykala, jyoti).
parent(surykala, kavita).
parent(daji, jyoti).
parent(daji, kavita).

wife(X, Y) :- husband(Y, X).

% Rules: Define father and mother relationships based on parent relationship
father(X, Y) :- male(X), parent(X, Y).
mother(X, Y) :- female(X), parent(X, Y).

% Rules: Define father and mother in law relationships based on parent relationship
father_in_law(X, Y) :- male(X), parent(X, Z), husband(Z, Y); male(X), parent(X, Z), wife(Z, Y).
mother_in_law(X, Y) :- female(X), parent(X, Z), husband(Z, Y); male(X), parent(X, Z), wife(Z, Y).

% Son and Daughter relationships
son(X, Y) :- male(X), parent(Y, X).
daughter(X, Y) :- female(X), parent(Y, X).

% Son and Daughter in law relationships
son_in_law(X, Y) :- male(X), parent(Y, Z), wife(Z,X).
daughter_in_law(X, Y) :- female(X), parent(Y, Z), wife(X, Z).

% Rules: Define grandfather and grandmother relationships based on parent relationship
grandfather(X, Z) :- father(X, Y), parent(Y, Z).
grandmother(X, Z) :- mother(X, Y), parent(Y, Z).

grandnephew(X,Y) :- grandfather(Z, X), sibling(Z, Y), male(X);nephew(Z, Y), son(X, Z); neice(Z, Y), son(X, Z).
grandneice(X,Y) :- grandfather(Z, X), sibling(Z, Y), female(X); nephew(Z, Y), daughter(X, Z); neice(Z, Y), daughter(X, Z).

% Rules: Define grandson and granddaughter relationships based on parent relationship
grandson(X, Z) :- son(X, Y), parent(Z, Y).
granddaughter(X, Z) :- daughter(X, Y), parent(Z, Y).

% Rules: Define greatson and greatdaughter relationships based on parent relationship
great_grand_son(X, Z) :- son(X, Y), grandfather(Z, Y).
great_grand_daughter(X, Z) :- daughter(X, Y), grandfather(Z, Y).

% Rules: Define sibling relationship based on parent relationship
sibling(X, Y) :- parent(Z, X), parent(Z, Y), X \= Y.

% Rules: Define cousin relationship based on parent relationship
cousin(X, Y) :- parent(Z, X), parent(W, Y), sibling(Z, W).

% Rules: Define uncle and aunty relationships based on parent relationship
uncle(X, Y) :- parent(Z, Y), sibling(X, Z), male(X); wife(Z, X), parent(W, Y), sibling(Z, W).
aunt(X, Y) :- cousin(Z, Y), mother(X, Z), female(X); father(Z, Y), sibling(X, Z), female(X).

% Rules: Define second uncle and second aunty relationships based on parent relationship
second_uncle(X, Y) :- great_uncle(Z, Y), male(X), parent(Z, X).
second_aunt(X, Y) :- great_uncle(Z, Y), female(X), parent(Z, X); second_uncle(Z, Y), wife(X, Z).

% Rules: Define nephew and neice relationships based on parent relationship
nephew(X, Y) :- male(X), parent(Z, X), sibling(Z, Y); uncle(Z, X), husband(Z, Y),male(X); uncle(Y, X), male(X). 
neice(X, Y) :- female(X), parent(Z, X), sibling(Z, Y); uncle(Z, X), husband(Z, Y),female(X); uncle(Y, X), female(X).

% Rules: Define second nephew and second neice relationships based on parent relationship
second_nephew(X, Y) :- father(Z,X), cousin(Z,Y), male(Y); aunt_in_law(Z, Y), grandson(X, Z); aunt(Z, Y), grandson(X, Z).
second_neice(X, Y) :- second_uncle(Y, X), female(X).

neice_in_law(X, Y) :- female(X), nephew(Z, Y), husband(Z, X);aunt_in_law(Z, Y), granddaughter(X, Z).

% Assuming you have uncle/2 defined in your code
great_uncle(X, Y) :- uncle(X, Z), parent(Z, Y).

% Assuming you have aunt/2 defined in your code
great_aunt(X, Y) :- aunt(X, Z), parent(Z, Y).

% Assuming you have cousin/2 and son/2 defined in your code
second_cousin(X, Y) :- grandfather(Z, X), grandfather(W,Y),sibling(W, Z).

%Rules: Define great-grandfather relationship based on parent relationship
great_grandfather(X, Z) :- father(X, Y), grandfather(Y, Z).

cousin_in_law(X, Y) :- cousin(X, Z), husband(Z, Y); cousin(Y, Z), wife(X, Z); cousin(X, Z), wife(Z, Y); wife(Z, X), cousin(Z, Y). 

brother_in_law(X, Y) :- sibling(X, Z), husband(Z, Y), male(X); sibling(Y, Z), husband(X, Z); sister_in_law(Z,Y), husband(X, Z).

sister_in_law(X, Y) :- sibling(X, Z),  husband(Z, Y), female(X) ; husband(Z, X), sibling(Z, Y), female(X); father_in_law(Z, X), father_in_law(Z, Y),female(X); sibling(X, Z), wife(Z, Y),female(X).
uncle_in_law(X,Y) :- uncle(X, Z),husband(Z, Y).
aunt_in_law(X,Y) :- aunt(X, Z),husband(Z, Y).


grandfather_in_law(X, Y) :- grandfather(X, Z), husband(Z, Y).
granddaughter_in_law(X, Y) :- grandfather_in_law(Y, X).



% Your family relationships and rules here
find_relationship(X, Y) :-
    husband(X, Y), write(X), write(' is the husband of '), write(Y), nl.
find_relationship(X, Y) :-
    wife(X, Y), write(X), write(' is the wife of '), write(Y), nl.
find_relationship(X, Y) :-
    father(X, Y), write(X), write(' is the father of '), write(Y), nl.
find_relationship(X, Y) :-
    mother(X, Y), write(X), write(' is the mother of '), write(Y), nl.
find_relationship(X, Y) :-
    father_in_law(X, Y), write(X), write(' is the father in law of '), write(Y), nl.
find_relationship(X, Y) :-
    mother_in_law(X, Y), write(X), write(' is the mother in law of '), write(Y), nl.
find_relationship(X, Y) :-
    son(X, Y), write(X), write(' is the son of '), write(Y), nl.
find_relationship(X, Y) :-
    daughter(X, Y), write(X), write(' is the daughter of '), write(Y), nl.
find_relationship(X, Y) :-
    grandfather(X, Y), write(X), write(' is the grandfather of '), write(Y), nl.
find_relationship(X, Y) :-
    grandson(X, Y), write(X), write(' is the grandson of '), write(Y), nl.
find_relationship(X, Y) :-
    granddaughter(X, Y), write(X), write(' is the granddaughter of '), write(Y), nl.
find_relationship(X, Y) :-
    great_grand_son(X, Y), write(X), write(' is the great grandson of '), write(Y), nl.
find_relationship(X, Y) :-
    great_grand_daughter(X, Y), write(X), write(' is the great granddaughter of '), write(Y), nl.
find_relationship(X, Y) :-
    sibling(X, Y), write(X), write(' is the sibling of '), write(Y), nl.
find_relationship(X, Y) :-
    father(Z, Y), mother(X, Z), write(X), write(' is the grandmother of '), write(Y), nl.
find_relationship(X, Y) :-
    great_uncle(X, Y), write(X), write(' is the great-uncle of '), write(Y), nl.
find_relationship(X, Y) :-
    great_aunt(X, Y), write(X), write(' is the great-aunt of '), write(Y), nl.
find_relationship(X, Y) :-
    uncle(X, Y), write(X), write(' is the uncle of '), write(Y), nl.
find_relationship(X, Y) :-
    aunt(X, Y), write(X), write(' is the aunt of '), write(Y), nl.
find_relationship(X, Y) :-
    second_uncle(X, Y), write(X), write(' is the second uncle of '), write(Y), nl.
find_relationship(X, Y) :-
    second_aunt(X, Y), write(X), write(' is the second aunt of '), write(Y), nl.
find_relationship(X, Y) :-
    nephew(X, Y), write(X), write(' is the nephew of '), write(Y), nl.
find_relationship(X, Y) :-
    neice(X, Y), write(X), write(' is the neice of '), write(Y), nl.
find_relationship(X, Y) :-
    second_nephew(X, Y), write(X), write(' is the second nephew of '), write(Y), nl.
find_relationship(X, Y) :-
    second_neice(X, Y), write(X), write(' is the second neice of '), write(Y), nl.
find_relationship(X, Y) :-
    cousin(X, Y), write(X), write(' is the cousin of '), write(Y), nl.
find_relationship(X, Y) :-
    second_cousin(X, Y), write(X), write(' is the second cousin of '), write(Y), nl.
find_relationship(X, Y) :-
    cousin_in_law(X, Y), write(X), write(' is the cousin in law of '), write(Y), nl.
find_relationship(X, Y) :-
    brother_in_law(X, Y), write(X), write(' is the brother in law of '), write(Y), nl.
find_relationship(X, Y) :-
    sister_in_law(X, Y), write(X), write(' is the sister in law of '), write(Y), nl.
find_relationship(X, Y) :-
    son_in_law(X, Y), write(X), write(' is the son in law of '), write(Y), nl.
find_relationship(X, Y) :-
    daughter_in_law(X, Y), write(X), write(' is the daughter in law of '), write(Y), nl.
find_relationship(X, Y) :-
    neice_in_law(X, Y), write(X), write(' is the neice in law of '), write(Y), nl.
find_relationship(X, Y) :-
    grandnephew(X, Y), write(X), write(' is the grandnephew of '), write(Y), nl.
find_relationship(X, Y) :-
    grandneice(X, Y), write(X), write(' is the grandneice of '), write(Y), nl.
find_relationship(X, Y) :-
    uncle_in_law(X, Y), write(X), write(' is the uncle in law of '), write(Y), nl.
find_relationship(X, Y) :-
    aunt_in_law(X, Y), write(X), write(' is the aunt in law of '), write(Y), nl.
%find_relationship(X, Y) :-
%    paternal_cousin(X, Y), write(X), write(' is the paternal cousin of
%    '), write(Y), nl.
find_relationship(X, Y) :-
    great_grandfather(X, Y), write(X), write(' is the great-grandfather of '), write(Y), nl.
find_relationship(X, Y) :-
    grandfather_in_law(X, Y), write(X), write(' is the grand father in law of '), write(Y), nl.
find_relationship(X, Y) :-
    granddaughter_in_law(X, Y), write(X), write(' is the grand daughter in law of '), write(Y), nl.
find_relationship(X, Y) :- write('Relationship not defined between '), write(X), write(' and '), write(Y), nl.
