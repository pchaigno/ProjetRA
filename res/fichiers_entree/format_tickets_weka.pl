#!/usr/bin/perl -w

if($#ARGV != 0) {
	die "Usage: $0 in\n";
}

my $in = $ARGV[0];
(my $name, my $ext) = split('\.', $in);
my $out = "$name.csv";

open(DAT, "< $in") or die "Unable to read $in:\n$!\n";
open(OUT, "> $out") or die "Unable to write $out:\n$!\n";

while (my $content = <DAT>){
	$content =~ tr/[\b\t]/,/;
 	print OUT "$content";
};

close(DAT);
close(OUT);
