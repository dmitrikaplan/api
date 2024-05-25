FROM bellsoft/liberica-openjdk-alpine as build

WORKDIR /source
COPY /gradle /source/gradle
COPY /src /source/src
COPY build.gradle.kts /source/
COPY gradlew /source
COPY settings.gradle.kts /source

RUN --mount=type=tmpfs,target=/build \
	--mount=type=tmpfs,target=/tmp \
	--mount=type=tmpfs,target=/root \
	cp -a /source /build \
	&& cd /build/source \
	&& ./gradlew build \
	&& cp /build/source/build/libs/api.jar /api.jar

#--------------------------------------------------------------------------#

FROM bellsoft/liberica-openjdk-alpine

COPY --from=build /api.jar /api.jar
CMD java -jar api.jar --spring.profiles.active=prod
