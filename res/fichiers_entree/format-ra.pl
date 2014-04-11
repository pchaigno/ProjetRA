#!/usr/bin/perl -w
use constant false => 0;
use constant true => 1;

my @dictionary;
my $word;
my $virg = 0;
my $line;

if($#ARGV != 2) {
	die "Usage: $0 dictionary in out\n";
}
my $file_in = $ARGV[0];
my $data = $ARGV[1];
my $file_out = $ARGV[2];

open(OUT, ">$file_out") or die "Unable to write $file_out:\n$!\n";

# Iterates on all words from the dictionary to build the list:
open(IN, $file_in) or die "Unable to read $file_in:\n$!\n";
while(<IN> =~ /([^\n]+)\n/) {
	push @dictionary, $1;
}
print OUT "\n";
close IN;

# Builds the ouput file:
open(DATA, $data) or die "Unable to read $data:\n$!\n";
while($line = <DATA>) {
	my $empty_line = 1;
	for(my $i=0; $i<=$#dictionary; $i++) {
		$word = $dictionary[$i];
		print "$word\n";
		# Print word's number if the word is in the article:
		if($line =~ /\b$word\b/) {
			print OUT ($i+1).' ';
			$empty_line = 0;
		}
	}
	# Doesn't print empty lines:
	if(!$empty_line) {
		print OUT "\n";
	}
}
close DATA;

close OUT;
