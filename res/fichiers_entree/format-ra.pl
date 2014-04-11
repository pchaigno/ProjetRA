#!/usr/bin/perl
use strict;

use constant false => 0;
use constant true => 1;

my @dictionary;

if($#ARGV != 2) {
	die "Usage: $0 dictionary in out\n";
}
my $dictionary_file = $ARGV[0];
my $data_file = $ARGV[1];
my $output_file = $ARGV[2];

# Iterates on all words from the dictionary to build the list:
open(DICTIONARY, "< $dictionary_file") or die "Unable to read $dictionary_file:\n$!\n";
while(my $word = <DICTIONARY>) {
	chomp($word);
	push(@dictionary, $word);
}
close(DICTIONARY);

# Builds the ouput file:
open(OUT, "> $output_file") or die "Unable to write $output_file:\n$!\n";
open(ARTICLES, "< $data_file") or die "Unable to read $data_file:\n$!\n";
while(my $article = <ARTICLES>) {
	my $empty_line = true;
	for(my $i=0; $i<=$#dictionary; $i++) {
		my $word = $dictionary[$i];
		# Print word's number if the word is in the article:
		if($article =~ /\b$word\b/) {
			print OUT ($i+1).' ';
			$empty_line = false;
		}
	}
	# Doesn't print empty lines:
	if(!$empty_line) {
		print OUT "\n";
	}
}

close(ARTICLES);
close(OUT);
