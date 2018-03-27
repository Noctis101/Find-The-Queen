# Find-The-Queen
This is a numbers game that has an offline version and an online version.

The aim is for one player to be a dealer and the other the spotter, with the
dealer hiding the queen and the spotter trying to guess where the queen is.

Working on my own, I decided to try an Agile-SCRUM approach with the
development of the offline being Sprint 1 and the online version
Sprint 2.

In doing so, it would negate any possible issue with the design of
the game and leave for any and all issues to be as a result of the client
and server aspect.

In the offline version, a GUI would need to be implemented in order to facilitate
logical implementation of carrying out the game, else the players will have to share
the same console which defeats the purpose of the game.

In the online version (utilizing TCP), two instances of the players (Client class)
should be created and these players connect to the Driver (Server class) and the game
carries out as it should with messages being sent to the respective players
throughout the game.

The usernames stored are the only one's authorized to play.

Currently, the limitations are that the players are hard-coded (as explained in
comments) as well as that the code is not complete (either semantic-wise or syntax-wise)
for clients toconnect to the server.
