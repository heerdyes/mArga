<?php
function search_cols( $allcols, $subcols )
{
  $idxlst = array();
  for( $i = 0; $i < count( $subcols ); $i++ )
  {
    $r = array_search( $subcols[$i], $allcols );
    if( gettype( $r ) === "integer" )
    {
      $idxlst[] = $r;
    }
    else
    {
      throw new Exception( "no such column: $subcols[$i]" );
    }
  }
  return $idxlst;
}

function subarray( $srca, $idxs )
{
  $r = array();
  for( $j = 0; $j < count( $idxs ); $j++ )
  {
    $r[] = $srca[$idxs[$j]];
  }
  return $r;
}

function print_row( $ra )
{
  for( $i = 0; $i < count( $ra ); $i++ )
  {
    echo "$ra[$i]\t";
  }
  echo "\n";
}

function delimjoin( $delim, $arr )
{
  $buf  = "";
  $last = count( $arr ) - 1;
  for( $i = 0; $i < $last; $i++ )
  {
    $buf = $buf . $arr[$i] . $delim;
  }
  $buf = $buf . $arr[$last];
  return $buf;
}
?>
