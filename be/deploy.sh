sudo docker ps -a -q --filter "name=redis" | grep -q . && docker stop redis && docker rm redis | true
sudo docker ps -a -q --filter "name=getabeer" | grep -q . && docker stop getabeer && docker rm getabeer | true

./gradlew clean build

sudo docker rmi gnidinger/getabeer:0.0.1
# sudo docker rmi gnidinger/getabeer:0.0.2

docker build -t gnidinger/getabeer:0.0.1 .
docker push gnidinger/getabeer:0.0.1

# docker build -t gnidinger/getabeer:0.0.2 --platform linux/amd64 .
# docker push gnidinger/getabeer:0.0.2