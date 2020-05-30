FROM python:3

RUN apt update
RUN apt install libpq-dev

RUN mkdir /www
ADD requirements.txt /www/requirements.txt
RUN pip3 install -r /www/requirements.txt

WORKDIR /www/

ENV FLASK_ENV development

# Activar modo debug para que la app se reinicie al detectar un cambio
ENV FLASK_DEBUG 1

# Nombre del archivo ejecutable Flask
ENV FLASK_APP application.py


ENTRYPOINT [ "python", "-m", "flask", "run", "--host=0.0.0.0", "--port=3333" ]
