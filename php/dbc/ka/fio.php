<?php
function get_lns( $fn )
{
  $fc = file_get_contents( $fn );
  $fc = trim( $fc );
  $lns = explode( "\n", $fc );
  return $lns;
}
?>
