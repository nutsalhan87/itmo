<?php declare(strict_types=1);

$db = fopen("results.csv", "r");

while (true) { 
    $line = fgets($db);
    if(!$line) {
        break;
    }
    $words = explode(",", $line);
    echo "<tr>\n";
    echo "\t<td>$words[0]</td>\n";
    echo "\t<td>$words[1]</td>\n";
    echo "\t<td>$words[2]</td>\n";
    echo "\t<td>$words[3]</td>\n";
    echo "\t<td>$words[4]</td>\n";
    echo "\t<td>$words[5]</td>\n";
    echo "</tr>\n";
    }

fclose($db);

?>