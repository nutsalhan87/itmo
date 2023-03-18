<?php declare(strict_types=1);

$timestamp = microtime(true);

function is_on_right_up(int $x, float $y, float $r) {
    if ($x < 0 || $y < 0) {
        return false;
    }

    return $x**2 + $y**2 <= $r**2;
}

function is_on_right_down(int $x, float $y, float $r) {
    if ($x < 0 || $y > 0) {
        return false;
    }

    return $y >= -$r && $x <= $r / 2;
}

function is_on_left_down(int $x, float $y, float $r) {
    if ($x > 0 || $y > 0) {
        return false;
    }

    return $y >= -$x - $r;
}

$current_date = new DateTime("now", new DateTimeZone("Europe/Moscow"));
$current_date = $current_date->format("d.m.y H:i:s");

$JSON = json_decode(file_get_contents('php://input'));

$x = (int)$JSON->x;
$y = (float)$JSON->y;
$r = (float)$JSON->r;

$answer = (is_on_right_up($x, $y, $r) || is_on_left_down($x, $y, $r) || is_on_right_down($x, $y, $r)) ? "ДА" : "НЕТ(((((";

$work_time = round((microtime(true) - $timestamp) * 1000);

echo "<tr>\n";
echo "\t<td>$current_date</td>\n";
echo "\t<td>$work_time мс</td>\n";
echo "\t<td>$x</td>\n";
echo "\t<td>$y</td>\n";
echo "\t<td>$r</td>\n";
echo "\t<td>$answer</td>\n";
echo "</tr>\n";

$db = fopen("results.csv", "a");
$out = "$current_date,$work_time мс,$x,$y,$r,$answer\n";
fwrite($db, $out);
fclose($db);

?>