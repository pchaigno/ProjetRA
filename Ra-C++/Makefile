objects = main.o Item.o Apriori.o Database.o
Cpp = g++
flags = -O6 -Wall

apriori : $(objects) Makefile
	$(Cpp) $(flags) $(objects) -o apriori

Item.o : Item.cpp Item.h
	$(Cpp) -c $(flags) Item.cpp

Apriori.o : Apriori.cpp Apriori.h Database.h Item.h
	$(Cpp) -c $(flags) Apriori.cpp

Database.o : Database.cpp Database.h
	$(Cpp) -c $(flags) Database.cpp

clean :
	rm -f $(objects) apriori
