#!/usr/bin/perl -w
use constant false => 0;
use constant true => 1;

my @dictionary;
my $word;
my $virg = false;
my $line;

if($#ARGV != 3) {
	die "Usage: $0 dictionary in nb_articles out\n";
}
my $file_in = $ARGV[0];
my $data = $ARGV[1];
my $nbArticles = $ARGV[2];
my $file_out = $ARGV[3];

# first step : construct the dictionnary
open(IN, $file_in) or die "Unable to read $file_in:\n$!\n";
open(OUT, ">$file_out") or die "Unable to write $file_out:\n$!\n";

while(<IN> =~ /([^\n]+)\n/) {
	$word = $1;

	push @dictionary, $word;

	#print comma if necessary
	if($virg) {
		print OUT ", ";
	} else {
		$virg = true;
	}

	#print the word in the fisrt line of the csv
	print OUT "$word";
}
print OUT "\n";
$virg = false;
close IN;

# second step : construct the rest of the csv
open(DATA, $data) or die "unable to read $data:\n$!\n";
while($line = <DATA>) {
	for(my $i=0; $i<=$#dictionary; $i++) {
		#print comma if necessary
		if($virg) {
			print OUT ", ";
		} else {
			$virg = true;
		}

		$word = $dictionary[$i];
		if($line =~ /\b$word\b/) {
			print OUT "Yes";
		} else {
			print OUT "No";
		}
	}
	print OUT "\n";
	$virg = false;
	$nbArticles++;
}

close DATA;
close OUT;

