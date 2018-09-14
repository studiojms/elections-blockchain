# learning-blockchain
Some simple examples learning blockchain with sawtooth

This project represents a sort of learning of blockchain

To run the project, follow the steps:

### dependencies:
- java 8
- nodejs 8

## service dependencies


- database and sawtooth

```docker-compose -f docker/dependencies.yaml up``` 

##frontend

- go to **blockchain-api**
- run ```npm install```
- then execute ```node index.js```


###generating data

```
http://localhost:8088/magic/generate/roles
```
```
http://localhost:8088/magic/generate/owner
```