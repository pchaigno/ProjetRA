#!/usr/bin/perl
use strict;
use utf8;

use constant false => 0;
use constant true => 1;

my %attributes;
my $count = 0;

%{$attributes{1}} = (1 => 2 , 2 => 4, 3 => 6);
%{$attributes{2}} = (1 => 3 , 2 => 6, 3 => 9);

my $a = get_item(2, 3);
my $b = get_item(2, 6);
my $c = get_item(1, 5);

print "$a\n";
print "$b\n";
print "$c\n";

# get the item corresponding to the given attribute and value
sub get_item{
	(my $attr, my $val) = @_;
	my %values = %{$attributes{$attr}};
	if(!exists($values{$val})) {
		$values{$val} = $count;
		$count++;
	}
	return $values{$val};
}
