select name from spaceship where name not in (select spaceship_name from spaceship_can_land_planet);