<?php
require_once "utl.php";
require_once "fio.php";

function all_from_table( $tblnm )
{
  $lns = get_lns( "$tblnm.psv" );
  $rs  = array();
  for( $i = 1; $i < count( $lns ); $i++ )
  {
    $rs[] = explode( "|", $lns[$i] );
  }
  return $rs;
}

function cols_from_table( $tblnm, $collst )
{
  if( count( $collst ) === 0 )
    return all_from_table( $tblnm );
  $lns = get_lns( "$tblnm.psv" );
  $iar = search_cols( explode( "|", $lns[0] ), $collst );
  $rs  = array();
  //
  for( $i = 1; $i < count( $lns ); $i++ )
  {
    $cs = explode( "|", $lns[$i] );
    $rs[] = subarray( $cs, $iar );
  }
  return $rs;
}

function create_table( $tblnm, $cols )
{
  $buf = delim_join( "|", $cols ) . "\n";
  file_put_contents( "$tblnm.psv", $buf );
}

function insert_into_table( $tblnm, $row )
{
  $buf = delim_join( "|", $row ) . "\n";
  file_put_contents( "$tblnm.psv", $buf, FILE_APPEND );
}
?>
