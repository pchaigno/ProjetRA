#!/usr/bin/perl
use strict;
use utf8;

use constant false => 0;
use constant true => 1;

my %attributes;
my $count = 1;

if($#ARGV < 2) {
	die "Usage: $0 in out filters\n";
}

my $data_file = $ARGV[0];
my $output_file = $ARGV[1];
my $filters_file = $ARGV[2];

# filters file reading
open(FILTERS, "<:encoding(UTF-8)", $filters_file) or die "Unable to read $filters_file:\n$!\n";
my $filters = <FILTERS>;
my @filters_table = split(/[\b\t\s]+/, $filters);
close(FILTERS);

# initilize the attributes hash table
for(my $i=0; $i<=$#filters_table; $i++) {
		my $key = $i;
		%{$attributes{$key}} = ();
}

# Builds the ouput file:
open(OUT, "> $output_file") or die "Unable to write $output_file:\n$!\n";
open(ARTICLES, "<:encoding(UTF-8)", $data_file) or die "Unable to read $data_file:\n$!\n";
my $line_number = 0;
while(my $line = <ARTICLES>) {
	if($line_number > 0) {
		$line =~ tr/ÀÁÂÃÄÅÇÈÉÊËÌÍÎÏÑÒÓÔÕÖÙÚÛÜÝàáâãäåçèéêëìíîïñòóôõöùúûüýÿ/AAAAAACEEEEIIIINOOOOOUUUUYaaaaaaceeeeiiiinooooouuuuyy/;
		my @value = split(/[\b\t]+/, $line);
		my $empty_line = true;
		for(my $i=0; $i<=$#filters_table; $i++) {
			my $type = $filters_table[$i];
			my $item;
			
			# type = number(_number)* => each number is a stage of the discretization
			if($type =~ /[1..9]+(_[1..9]+)*/) {
				my $stage = stage($value[$i], $type);
				$item = get_item($i, $stage);
				print OUT ($item).' ';
				
			# type = bool => the attribute is boolean (some possibles values : True, T, F, False, Yes, No, Y, N)
			} elsif($type =~ /bool/ && ($value[$i] =~ /T/ || $value[$i] =~ /Y/)) {
				$item = get_item($i, true);
				print OUT ($item).' ';
				
			# type = symbol => symbolic attribute
			} elsif($type =~ /symbol/) {
				$item = get_item($i, $value[$i]);
				print OUT ($item).' ';
			}
			
			# type = ignore => the column will be ignored
			
			$empty_line = false;
		}
		# Doesn't print empty lines:
		if(!$empty_line) {
			print OUT "\n";
		}
	}
	$line_number++;
}

close(ARTICLES);
close(OUT);

# param $value : numeric value of an attribute
# param $stages : different stages used for the discretization of the attribute
# return :
# 	"min" if $value < min($stage)
#	"max" if $value > max($stage)
#	else, the direct greater stage
sub stage{
	my ($value, $stages) = @_;
	my @list = split("_", $stages);
	if($value < $list[0]) {
		return "min";
	}
	for(my $i = 1 ; $i <= $#list ; $i++) {
		if($value < $list[$i]) {
			return $list[$i];
		}
	}
	return "max";
}

# get the item corresponding to the given attribute and value
sub get_item{
	(my $attr, my $val) = @_;
	if(!exists(${$attributes{$attr}}{$val})) {
		${$attributes{$attr}}{$val} = $count;
		$count++;
	}
	return ${$attributes{$attr}}{$val};
}
