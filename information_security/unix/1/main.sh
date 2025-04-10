if [ "$(id -u)" != "0" ]; then
    echo You must have root priveleges
    exit 1
fi


# 1. Создайте пользователя sXXXXXX (где XXXXXX - ваш номер ису).
# Создайте группу пользователей studs, добавьте пользователя в эту группу.

useradd s335059
passwd s335059
groupadd studs
usermod -aG studs s335059

cat /etc/passwd | grep s335059
cat /etc/group | grep studs

# groupdel studs
# userdel s335059


# 2. Создайте пользователя admin_sXXXXXX (где XXXXXX - ваш номер ису).
# Предоставьте пользователю root-права.

useradd admin_s335059
passwd admin_s335059
# userdel admin_s335059

# 1 способ
usermod -aG wheel admin_s335059
cat /etc/group | grep wheel
# usermod -rG wheel admin_s335059

# 2 способ
gpasswd -a admin_s335059 wheel
cat /etc/group | grep wheel
# gpasswd -d admin_s335059 wheel

# 3 способ
echo "admin_s335059 ALL=(ALL:ALL) ALL" >> /etc/sudoers
cat /etc/sudoers | grep admin_s335059
# sed -i '$ d' /etc/sudoers

# 4 способ
echo "users.users.admin_s335059 = {
    isNormalUser = true;
    extraGroups = [ \"wheel\" ];
};"
nixos-rebuild switch --flake .

# 3. Продемонстрируйте, что пользователь admin_sXXXXXX(где XXXXXX - ваш номер ису),
# теперь имеет больше привилегий, по сравнению с пользователем user_sXXXXXX.

# Отличие 1 - read root file
# Отличие 2 - write into root file
# Отличие 3 - execute root file
# Отличие 4 - get entries of root directory
# Отличие 5 - create file in root directory


# Вариант 4
# Убрать возможность создания группы по умолчанию для новых пользователей без группы.
cat /etc/login.defs | grep USERGROUPS_ENAB
sed -i 's/USERGROUPS_ENAB yes/USERGROUPS_ENAB no/' /etc/login.defs
# sed -i 's/USERGROUPS_ENAB no/USERGROUPS_ENAB yes/' /etc/login.defs