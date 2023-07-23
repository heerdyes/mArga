<?php
require_once "dbc.php";

function test_all_from_table()
{
  $ca = all_from_table('courses');
  if( $ca[1][0] === 'jv101' )
  {
    echo "PASS\n";
  }
  else
  {
    echo "FAIL\n";
  }
}

function test_cols_from_table()
{
  $cols = ['ccode', 'dur'];
  $sel = cols_from_table( 'courses', $cols );
  print_row( $cols );
  echo "-----------\n";
  for( $i = 0; $i < count( $sel ); $i++ )
  {
    print_row( $sel[$i] );
  }
}

test_cols_from_table();
?>
