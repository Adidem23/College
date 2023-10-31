% Define the knowledge base with game attributes
game(pubg,[fps,singleplayer,console,military_theme,modern_setting,4.0]).
game(cod, [fps, multiplayer, console, military_theme, modern_setting, 4.5]).
game(modernwar, [fps, multiplayer, console, military_theme, modern_setting, 4.7]).
game(gta, [adventure, multiplayer, console, open_world, urban_setting, 4.8]).
game(minecraft, [sandbox, multiplayer, console, open_world, block-building, 4.5]).
game(battlefield, [fps, multiplayer, pc, military_theme, modern_setting, 4.6]).
game(hunter, [adventure, multiplayer, pc, open_world, medieval_setting, 4.3]).
game(runstars, [sandbox, multiplayer, pc, space_theme, futuristic_setting, 4.2]).
game(contra, [fps, singleplayer, console, military_theme, retro_setting, 4.0]).
game(spacestar, [adventure, singleplayer, pc, space_theme, futuristic_setting, 4.4]).
game(blaston, [sandbox, singleplayer, console, futuristic_theme, futuristic_setting, 4.1]).
game(apexlegends, [fps, multiplayer, pc, futuristic_theme, urban_setting, 4.7]).
game(zelda, [adventure, multiplayer, pc, fantasy_theme, fantasy_setting, 4.8]).
game(spiderman, [adventure, singleplayer, console, superhero_theme, urban_setting, 4.9]).
game(blink, [sandbox, singleplayer, console, fantasy_theme, fantasy_setting, 4.2]).
game(reddead, [sandbox, singleplayer, console, western_theme, open_world_setting, 4.6]).
game(overwatch, [fps, multiplayer, pc, sci-fi_theme, futuristic_setting, 4.3]).
game(doom, [fps, singleplayer, pc, horror_theme, futuristic_setting, 4.7]).
game(halo, [fps, multiplayer, console, sci-fi_theme, futuristic_setting, 4.5]).
game(warcraft, [fps, multiplayer, pc, fantasy_theme, fantasy_setting, 4.8]).
game(darksouls, [action, singleplayer, console, dark_fantasy_theme, medieval_setting, 4.9]).

% Check if a game fits the user's preferences (backward chaining)
check_game_preferences(Game) :-
    game(Game, [Genre, SingleOrMultiplayer, Console, Theme, Setting, Rating]),
    ask_preferences('genre', Genre),
    ask_preferences('singleplayer/multiplayer', SingleOrMultiplayer),
    ask_preferences('console/pc', Console),
    ask_preferences('theme', Theme),
    ask_preferences('setting', Setting),
    ask_preferences('rating', Rating),
    format('The game ~w suits your preferences.', [Game]).

% Ask user preferences
ask_preferences(AttributeLabel, Attribute) :-
    format('Do you like ~w games? (yes/no) ', [AttributeLabel]),
    read(Answer),
    Answer == yes.

% Suggest a game based on user preferences (forward chaining)
suggest_game :-
    write('What genre do you like? (fps/adventure/sandbox): '),
    read(Genre),
    write('Do you prefer singleplayer or multiplayer games? (singleplayer/multiplayer): '),
    read(SingleOrMultiplayer),
    write('Do you prefer console or PC games? (console/pc): '),
    read(Console),
    write('Do you like games with a specific theme? (military_theme/open_world/space_theme or "any"): '),
    read(Theme),
    write('Do you like games set in a specific environment? (modern/block/retro or "any"): '),
    read(Setting),
    write('What is your minimum preferred rating (e.g., 4.0): '),
    read(MinRating),
    find_matching_game(Genre, SingleOrMultiplayer, Console, Theme, Setting, MinRating, Game),
    format('Recommended game to play: ~w', [Game]).

find_matching_game(Genre, SingleOrMultiplayer, Console, Theme, Setting, MinRating, Game) :-
    game(Game, [Genre, SingleOrMultiplayer, Console, Theme, Setting, Rating]),
    Rating >= MinRating.

% Entry point
start:-
    write('Welcome to the Game Recommender System!'), nl,
    write('type start. to start the Recommender'), nl,
    suggest_game.
