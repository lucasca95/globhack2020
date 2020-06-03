import os
import sys
from datetime import datetime, date, time
from flask import Flask
from flask_sqlalchemy import SQLAlchemy 
from flask_restful import Api, reqparse, abort, Resource, fields, marshal_with


app = Flask(__name__)
app.secret_key = os.environ.get("SECRET_KEY")
print(f'\n\nDRIVER:\n{os.environ.get("DATABASE_URL")}\n', file=sys.stderr)
app.config['SQLALCHEMY_DATABASE_URI'] = os.environ.get("DATABASE_URL")
app.config['SQLALCHEMY_DATABASE_URI'] = 'postgresql+psycopg2://lucasca95:admin@db_postgresql:5432/db'
print(f'\n\nSQL_DRIVER:\n{app.config["SQLALCHEMY_DATABASE_URI"]}\n', file=sys.stderr)

db = SQLAlchemy(app)

######################################
###### Modelos
class UserHelped(db.Model):
    __tablename__ = 'helped'
    id = db.Column(db.Integer, primary_key=True)
    first_name = db.Column(db.String(20))
    last_name = db.Column(db.String(20))
    neighborhood = db.Column(db.String(100))
    birthdate = db.Column(db.DateTime)
    email = db.Column(db.String(30))
    password = db.Column(db.String(100))
    _type = db.Column(db.String(1))
    rating = db.Column(db.Float)
    # Relation with Petition
    petitions = db.relationship('Petition', backref='helped')
    # Relation with ReviewHelped
    reviews = db.relationship('ReviewHelped', backref='user')

    def __init__(self, first_name=None, last_name=None, neighborhood=None, birthdate=None, email=None, password=None, _type=None, rating=None):
        self.first_name = first_name
        self.last_name = last_name
        self.neighborhood = neighborhood
        self.birthdate = birthdate
        self.email = email
        self.password = password
        self._type = _type
        self.rating = rating

    def serialize(self):
       """Return object data in easily serializeable format"""
       return {
           'id'  : self.id,
           'first_name': self.first_name,
           'last_name': self.last_name,
           'neighborhood': self.neighborhood,
           'birthdate': self.birthdate,
           'email': self.email,
           'type': self._type,
           'rating': self.rating
        }

class ReviewHelped(db.Model):
    __tablename__ = 'reviewhelped'
    id = db.Column(db.Integer, primary_key=True)
    comment = db.Column(db.String(140))
    rating = db.Column(db.Float)
    # Relation with User
    user_id = db.Column(db.Integer, db.ForeignKey('helped.id'))
    # Relation with Petition
    petition_id = db.Column(db.Integer, db.ForeignKey('petition.id'))

    def __init__(self, comment=None, rating=None, gift=None):
        self.comment = comment
        self.rating = rating

    def serialize(self):
       """Return object data in easily serializeable format"""
       return {
           'id'  : self.id,
           'comment': self.comment,
           'rating': self.rating
        }

class UserCollaborator(db.Model):
    __tablename__ = 'collaborator'
    id = db.Column(db.Integer, primary_key=True)
    first_name = db.Column(db.String(20))
    last_name = db.Column(db.String(20))
    neighborhood = db.Column(db.String(100))
    birthdate = db.Column(db.DateTime)
    email = db.Column(db.String(30))
    password = db.Column(db.String(100))
    _type = db.Column(db.String(1))
    rating = db.Column(db.Float)
    # Relation with Petition
    petitions = db.relationship('Petition', backref='collaborator')
    # Relation with ReviewCollaborator
    reviews = db.relationship('ReviewCollaborator', backref='user')

    def __init__(self, first_name=None, last_name=None, neighborhood=None, birthdate=None, email=None, password=None, _type=None, rating=None):
        self.first_name = first_name
        self.last_name = last_name
        self.neighborhood = neighborhood
        self.birthdate = birthdate
        self.email = email
        self.password = password
        self._type = _type
        self.rating = rating

    def serialize(self):
        return {
           'id'  : self.id,
           'first_name': self.first_name,
           'last_name': self.last_name,
           'neighborhood': self.neighborhood,
           'birthdate': self.birthdate,
           'email': self.email,
           'type': self._type,
           'rating': self.rating
        }

class ReviewCollaborator(db.Model):
    __tablename__ = 'reviewcollaborator'
    id = db.Column(db.Integer, primary_key=True)
    comment = db.Column(db.String(140))
    rating = db.Column(db.Float)
    # Relation with UserCollaborator
    user_id = db.Column(db.Integer, db.ForeignKey('collaborator.id'))
    # Relation with Petition
    petition_id = db.Column(db.Integer, db.ForeignKey('petition.id'))

    def __init__(self, comment=None, rating=None, gift=None):
        self.comment = comment
        self.rating = rating

    def serialize(self):
       return {
           'id'  : self.id,
           'comment': self.comment,
           'rating': self.rating
        }

class Petition(db.Model):
    __tablename__ = 'petition'
    id = db.Column(db.Integer, primary_key=True)
    day = db.Column(db.DateTime)
    hour = db.Column(db.DateTime)
    status = db.Column(db.String(20))
    neighborhood = db.Column(db.String(100))
    gift = db.Column(db.String(100))

    # Relation with UserHelped
    helped_id = db.Column(db.Integer, db.ForeignKey('helped.id'))
    # Relation with UserCollaborator
    collaborator_id = db.Column(db.Integer, db.ForeignKey('collaborator.id'))
    
    # Relation with ReviewHelped
    review_helped = db.relationship('ReviewHelped', backref='petition')
    # Relation with ReviewCollaborator
    review_collaborator = db.relationship('ReviewCollaborator', backref='petition')


    def __init__(self, day=None, hour=None, status=None, neighborhood=None, gift=None):
        self.day = day
        self.hour = hour
        self.status = status
        self.neighborhood = neighborhood
        self.gift = gift

    def serialize(self):
        return {
           'id'  : self.id,
           'day': self.day,
           'hour': self.hour,
           'status': self.status,
           'neighborhood': self.neighborhood,
           'gift': self.gift
        }

######################################

######################################
###### DB Access
def findUserHelpedById(user_id):
    u = UserHelped.query.get(user_id)
    print(f'\n\nUser:\n{u}\n')
    if (u):
        return u
    else:
        abort(404, error=f'UserHelped with id {user_id} not found')
    
def findUserCollaboratorById(user_id):
    u = UserCollaborator.query.get(user_id)
    print(f'\n\nUser:\n{u}\n')
    if (u):
        return u
    else:
        abort(404, error=f'UserCollaborator with id {user_id} not found')

def save(e):
    db.session.add(e)
    db.session.commit()
    return e

def delete(e):
    db.session.delete(e)
    db.session.commit()
    
######################################

######################################
###### API Resources
# UserHelped
parser_user_helped = reqparse.RequestParser()
parser_user_helped.add_argument('first_name')
parser_user_helped.add_argument('last_name')
parser_user_helped.add_argument('neighborhood')
parser_user_helped.add_argument('birthdate')
parser_user_helped.add_argument('email')
parser_user_helped.add_argument('password')
parser_user_helped.add_argument('type')
parser_user_helped.add_argument('rating')
user_helped_fields = {
    'id': fields.Integer,
    'first_name': fields.String,
    'last_name': fields.String,
    'neighborhood': fields.String,
    # 'birthdate': fields.DateTime(dt_format='iso8601'),
    'birthdate': fields.String,
    'email': fields.String,
    'password': fields.String,
    'type': fields.String,
    'rating': fields.Float
}

# UserCollaborator
parser_user_collaborator = reqparse.RequestParser()
parser_user_collaborator.add_argument('first_name')
parser_user_collaborator.add_argument('last_name')
parser_user_collaborator.add_argument('neighborhood')
parser_user_collaborator.add_argument('birthdate')
parser_user_collaborator.add_argument('email')
parser_user_collaborator.add_argument('password')
parser_user_collaborator.add_argument('type')
parser_user_collaborator.add_argument('rating')
user_collaborator_fields = {
    'id': fields.Integer,
    'first_name': fields.String,
    'last_name': fields.String,
    'neighborhood': fields.String,
    # 'birthdate': fields.DateTime(dt_format='iso8601'),
    'birthdate': fields.String,
    'email': fields.String,
    'password': fields.String,
    'type': fields.String,
    'rating': fields.Float
}

# Petition
parser_petition = reqparse.RequestParser()
parser_petition.add_argument('day')
parser_petition.add_argument('hour')
parser_petition.add_argument('status')
parser_petition.add_argument('neighborhood')
parser_petition.add_argument('gift')
petition_fields = {
    'id': fields.Integer,
    'day': fields.String,
    'hour': fields.String,
    'status': fields.String,
    'neighborhood': fields.String,
    'gift': fields.String
}


class UserHelpedListAPI(Resource):
    @marshal_with(user_helped_fields)
    def get(self):
        users = UserHelped.query.all()
        return [u.serialize() for u in users]

    @marshal_with(user_helped_fields)
    def post(self):
        args = parser_user_helped.parse_args()
        u = UserHelped(
            args['first_name'], 
            args['last_name'],
            args['neighborhood'],
            date.fromisoformat(args['birthdate']), 
            args['email'], 
            args['password'], 
            args['type'],
            args['rating']
        )
        save(u)
        return u.serialize()

class UserHelpedAPI(Resource):
    @marshal_with(user_helped_fields)
    def delete(self, user_id):
        aux = findUserHelpedById(user_id)
        delete(findUserHelpedById(user_id))
        return aux.serialize()

class UserCollaboratorListAPI(Resource):
    @marshal_with(user_collaborator_fields)
    def get(self):
        users = UserCollaborator.query.all()
        return [u.serialize() for u in users]

    @marshal_with(user_collaborator_fields)
    def post(self):
        args = parser_user_helped.parse_args()
        u = UserCollaborator(
            args['first_name'], 
            args['last_name'], 
            args['neighborhood'],
            date.fromisoformat(args['birthdate']), 
            args['email'], 
            args['password'], 
            args['type'],
            args['rating']
        )
        save(u)
        return u.serialize()

class PetitionListAPI(Resource):
    @marshal_with(petition_fields)
    def get(self):
        petitions = Petition.query.all()
        return [p.serialize() for p in petitions]

class PetitionHelpedAPI(Resource):
    @marshal_with(petition_fields)
    def post(self, user_id):
        args = parser_petition.parse_args()
        p = Petition(
            args['day'],
            args['hour'],
            args['status'],
            args['neighborhood'],
            args['gift']
        )
        u = findUserHelpedById(user_id)
        p.helped = u
        save(p)
        return p.serialize()

class PetitionCollaboratorAPI(Resource):
    @marshal_with(petition_fields)
    def get(self, user_id):
        u_collaborator = findUserCollaboratorById(user_id)
        print(f'\n\nCollaborator:\n{u_collaborator.serialize()}\n', file=sys.stderr)
        petitions = Petition.query.filter_by(neighborhood=u_collaborator.neighborhood, status='waiting')
        return [p.serialize() for p in petitions]

class PetitionByNeighborhoodAPI(Resource):
    @marshal_with(petition_fields)
    def get(self, neighborhood):
        petitons = Petition.query.filter_by(neighborhood=neighborhood).all()
        return [p.serialize() for p in petitions]


###############################################################
@app.route('/', methods=['GET'])
def welcome():
    return "SERVER RUNNING"
###############################################################
###############################################################
@app.route('/dbinit', methods=['GET'])
def createdb():
    db.drop_all()
    db.create_all()
    u_lucas = UserCollaborator('Lucas', 'Camino', 'CityBell', date.fromisoformat('1995-12-13'), 'lucascamino@test.com',
                '123456789', 'C', 5)

    u_claudia = UserCollaborator('Claudia', 'Genchi', 'CityBell', date.fromisoformat('1967-02-24'), 'claudiagenchi@test.com',
                '123456789', 'C', 5)
    
    u_agustina = UserCollaborator('Agustina', 'Camino', 'CityBell', date.fromisoformat('2000-03-22'), 'agustinacamino@test.com',
                '123456789', 'C', 5)

    u_abuelo = UserHelped('Abuelo', 'Abuelo', 'CityBell', date.fromisoformat('1937-06-06'), 'abuelo@test.com', '123456789', 'H', 5)
    u_abuela = UserHelped('Abuela', 'Abuela', 'CityBell', date.fromisoformat('1937-06-06'), 'abuela@test.com', '123456789', 'H', 5)


    p_2 = Petition(datetime.now(), datetime.now(), 'done', ' ','Doy torta!')
    p_3 = Petition(datetime.now(), datetime.now(), 'done', ' ', 'Agradezco con 250 pesos!')
    p_4 = Petition(datetime.now(), datetime.now(), 'done', ' ', 'MUCHAS GRACIAS')
    p_5_waiting = Petition(datetime.now(), datetime.now(), 'waiting', ' ', 'Gracias!!!!!!')
    
    rev_p2_helped = ReviewHelped('Fue muy amable', 8.0)
    rev_p2_collaborator = ReviewCollaborator('Prometió torta y cumplió', 7.0)

    rev_p3_helped = ReviewHelped('Rechazó el dinero', 8.0)
    rev_p3_collaborator = ReviewCollaborator('Rechacé el dinero', 8.0)

    rev_p4_helped = ReviewHelped('Una persona mal educada', 2.0)
    rev_p4_collaborator = ReviewCollaborator('', 4.0)

    # Connect objects
    u_lucas.petitions.append(p_2)
    u_abuela.petitions.append(p_2)
    p_2.neighborhood = u_abuela.neighborhood
    rev_p2_collaborator.petition = p_2
    rev_p2_helped.petition = p_2
    rev_p2_collaborator.user = u_lucas
    rev_p2_helped.user = u_abuela
    #
    u_claudia.petitions.append(p_3)
    u_abuelo.petitions.append(p_3)
    p_3.neighborhood = u_abuelo.neighborhood
    rev_p3_collaborator.petition = p_3
    rev_p3_helped.petition = p_3
    rev_p3_collaborator.user = u_claudia
    rev_p3_helped.user = u_abuelo
    #
    u_lucas.petitions.append(p_4)
    u_abuela.petitions.append(p_4)
    p_4.neighborhood = u_abuela.neighborhood
    rev_p4_collaborator.petition = p_4
    rev_p4_helped.petition = p_4
    rev_p4_collaborator.user = u_agustina
    rev_p4_helped.user = u_abuela
    #
    u_abuelo.petitions.append(p_5_waiting)
    p_5_waiting.neighborhood = u_abuelo.neighborhood

    db.session.add(u_lucas)
    db.session.add(u_claudia)
    db.session.add(u_agustina)
    db.session.add(u_abuelo)
    db.session.add(u_abuela)
    
    db.session.add(p_2)
    db.session.add(p_3)
    db.session.add(p_4)
    db.session.add(p_5_waiting)

    db.session.add(rev_p2_helped)
    db.session.add(rev_p2_collaborator)
    db.session.add(rev_p3_helped)
    db.session.add(rev_p3_collaborator)
    db.session.add(rev_p4_helped)
    db.session.add(rev_p4_collaborator)

    db.session.commit()
    
    return f"Database restarted!"
###############################################################

api = Api(app)
api.add_resource(UserHelpedListAPI,'/helped', '/helped/')
api.add_resource(UserHelpedAPI,'/helped/<int:user_id>')

api.add_resource(PetitionListAPI,'/petitions', '/petitions/')

# PetitionHelpedAPI: genera un pedido de ayuda y se lo asocia al 
# usuario ayudado que hizo el request.
api.add_resource(PetitionHelpedAPI,'/petitions/helped/<int:user_id>')

# PetitionCollaboratorAPI: devuelve las peticiones que están pendientes en el rango
# de cuadras donde vive el usuario colaborador.
api.add_resource(PetitionCollaboratorAPI,'/petitions/collaborator/<int:user_id>')

# PetitionByNeighborhoodAPI: devuelve todas las peticiones que correspondan al 
# barrio indicado.
api.add_resource(PetitionByNeighborhoodAPI, '/petitions/<neighborhood>')

if __name__ == '__main__':
    app.run(host='0.0.0.0', port=3333)