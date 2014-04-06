#!/usr/bin/perl -w

# VARIABLES
my @dictionary;
my $word;
my $virg = 0;
my $line;

##########################################################################
# USAGE

my $usage = "Usage : $0
Mandatory
argument
*\t -out        <prefixe des fichiers de sortie>
*\t -in         <fichier de donnees a transformer>

 \t -h         : aide
\n";


die $usage unless ($#ARGV > -1);
my $file_in = $ARGV[0];
my $data = $ARGV[1];
my $nbart = $ARGV[2];
my $file_out = $ARGV[3];

# first step : construct the dictionnary
open(IN,$file_in) or die "unable to read $file_in $!\n";
open(OUT,"> $file_out") or die "unable to write $file_out $!\n";

my $nb_mots = 100;
my $k = 0;
while(($k < $nb_mots) && (<IN> =~ /([^\n]+)\n/)) {
	$word = $1;

	# look for the same word in the array
	my $trouve = 0;
	for(my $i = 0 ; $i <= $#dictionary && !$trouve ; $i++) {
		if($dictionary[$i] =~ /^$word$/) {
			$trouve = 1;
		}
	}

	#don't print the word if exists in the array
	if(!$trouve) {
		push @dictionary, $word;
		#print comma if necessary
		if($virg) {
			print OUT ", ";
		} else {
			$virg = 1;
		}

		#print the word in the fisrt line of the csv
		print OUT "$word";
	}
	$k++;
}
print OUT "\n";
$virg = 0;
close IN;

# second step : construct the rest of the csv
open(DATA,$data) or die "unable to read $data $!\n";

my $nb_art = 0;
while(($nb_art < $nbart) && ($line = <DATA>)) {
	for(my $i = 0 ; $i <= $#dictionary ; $i++) {
		
		#print comma if necessary
		if($virg) {
			print OUT ", ";
		} else {
			$virg = 1;
		}

		$word = $dictionary[$i];
		if($line =~ /$word/) {
			print OUT "Yes";
		} else {
			print OUT "No";
		}
	}
	print OUT "\n";
	$virg = 0;
	$nb_art++;
}

close DATA;
close OUT;

