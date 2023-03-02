# 가동중인 레디스 컨테이너 중단 및 삭제
sudo docker ps -a -q --filter "name=redis" | grep -q . && docker stop redis && docker rm redis | true
# sudo docker ps -a -q --filter "name=oscarfonts/h2" | grep -q . && docker stop oscarfonts/h2 && docker rm oscarfonts/h2 | true
sudo docker ps -a -q --filter "name=getabeer" | grep -q . && docker stop getabeer && docker rm getabeer | true

# 레디스 컨테이너 run
#sudo docker-compose up -d

# 기존 이미지 삭제
sudo docker rmi gnidinger/getabeer:0.0.1

# 도커허브 이미지 pull
sudo docker pull gnidinger/getabeer:0.0.1

sudo docker-compose up -d

# 사용하지 않는 불필요한 이미지 삭제 -> 현재 컨테이너가 물고 있는 이미지는 삭제되지 않음
docker rmi -f $(docker images -f "dangling=true" -q) || true
