# ElectronicVotingSystem

Elections are a very important aspect of every country's political life and as a such important thing it is neccesary to do everything
in order to make the voting process as simple and as reliable as possible. Voting in Bosnia is a really complex process since Bosnian 
authorities hold elections every 2 years, one for the municipality pairlaments and chiefs and the other for the three member presidency.
Voting is done on paper and the counting of votes is done by the people specialy selected and trained to do such obligations. 
The people selected for the counting process often work 30+ hours without a break and it often happens that they start making intentional 
or unintentional mistakes while counting. Also, the voting is done the same for every person. 
The person finds his voting place, shows an ID or drivers licence to the people working to prove that he is eligible to vote and then
the person votes. The process repeats for every single person troughout the day. After saying all of this, I believe we have more than
enough reasons to implement a simple, yet effective electronic voting system in our country. 


The EVS my friends and I will try to build consists of 3 parts. The first part is used before the election day, the second on election
day and the third part is the closing of the elections and the results. All of these parts will be connected to a Database but I will
mention it later on in the project explanation. First part is where the officials or the people in charge of organising the elections 
fill one database table with all the data about the potential voters and one database table with all the data about the candidates. 
DB Table with voters should consist of Name, Surname, unique ID and a boolean value showing if they already voted or not. 
DB Table with candidates is going to be simple and should consist only of Name and Last Name of the candidate and candidate's political
party. Inserting records is going to be done trough a GUI and might be password protected. 
The second part is the part that the voter can see. It is a GUI where the user enters his ID and the system checks it. 
We might try to implement a barcode scanner to scan the ID card the voter has and to check for the barcode content (unique ID)
in the database. The third part is again only for the authorities and it collects all the votes in one DB table and counts the
votes for each candidate and then gives the election results as an output to the screen or offers to create word document. 


The ending product may differ a bit from the given proposal but the frame of the project should stay the same, as explained. 
