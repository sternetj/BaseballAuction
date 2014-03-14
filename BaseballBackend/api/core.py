from utils import Utils
import cherrypy
import json
import uuid
import hashlib

class API(object):

  @cherrypy.expose
  def index(self):
    return "API"

  @cherrypy.expose
  def login(self, username):
    #body = json.loads( cherrypy.request.body.read() )
    matchingUsers = Utils.query("SELECT team_id FROM Team WHERE teamname=%s", username)
    
    if(len(matchingUsers) == 0):
      return "BAD"
    elif (len(matchingUsers) == 1):
      return "OK;%s" % matchingUsers[0]['team_id']
    else:
      return "ERORR"


  @cherrypy.expose
  def team(self, teamid):
    playersOnTeam = Utils.query("""SELECT p.player_name, o.cost, GROUP_CONCAT(DISTINCT pos.pos_name ORDER BY pos.pos_name DESC SEPARATOR '# ') as pos_name, o.seasonsHeld
                                    FROM Player p
                                    JOIN bteam b
                                    ON b.bteam_id = p.bteam_id
                                    JOIN canplay cp
                                    ON cp.pid = p.pid
                                    JOIN position pos
                                    ON pos.pos_id = cp.pos_id
                                    JOIN Owns o
                                    ON p.pid = o.pid
                                    JOIN Team t
                                    ON t.team_id = o.team_id AND t.team_id =%s
                                    GROUP BY p.player_name
                                    ORDER BY pos.sortOrder""", teamid)

    team = ""
    for player in playersOnTeam:
      team += ("%s,%s,%s,%s;" % (player["player_name"],player["cost"],player["pos_name"], player["seasonsHeld"]))
    
    return team

  @cherrypy.expose
  def auction(self):
      bid = Utils.query("""SELECT player_name, teamname, bid, image, bteam_name, GROUP_CONCAT(DISTINCT pos_name ORDER BY pos_name DESC SEPARATOR '# ') as pos_name
                                      From bid b
                                      JOIN player p
                                      ON b.pid = p.pid
                                      JOIN team t
                                      ON t.team_id = b.team_id
                                      JOIN bteam bt
                                      ON bt.bteam_id = p.bteam_id
                                      LEFT JOIN canplay cp
                                      ON cp.pid = b.pid
                                      LEFT JOIN position pos
                                      ON pos.pos_id = cp.pos_id
				      GROUP BY player_name""", ())

      if(len(bid) == 0):
        return "OK;Auction Will Resume Shortly,,-,http://www.clker.com/cliparts/K/s/M/V/6/T/timer-md.png,,,placheholder"
      return "OK;%s,%s,%s,%s,%s,%s" % (bid[0]['player_name'],bid[0]['teamname'],bid[0]['bid'],
                                        bid[0]['image'],bid[0]['bteam_name'],bid[0]['pos_name'])


  @cherrypy.expose
  def getPlayers(self):
      players = Utils.query("""SELECT player_name, t.bteam_name, team_id
                                FROM player p
                                LEFT JOIN owns o
                                ON p.pid = o.pid
                                JOIN bteam t
                                ON t.bteam_id = p.bteam_id""", ())

      playersString = ""
      for player in players:
        playersString += ("%s,%s,%s;" % (player["player_name"],player["bteam_name"],player["team_id"]))
    
      return playersString[:-1]

  @cherrypy.expose
  def getTeamPlayers(self, teamid):
    players = Utils.query("""SELECT player_name, pos_name, cost, p.pos_id
                    FROM owns o
                    JOIN Player p
                    ON o.pid = p.pid
                    JOIN Position pos
                    ON p.pos_id = pos.pos_id
                    WHERE team_id = %s""", teamid)
    
    playersString = ""
    for player in players:
      playersString += ("%s,%s,%s;" % (player["player_name"],player["pos_name"],player["cost"]))
    
    return playersString[:-1]

  @cherrypy.expose
  def setBidAmount(self, bid):
    Utils.execute("""UPDATE bid
                      SET bid =%s""", (bid))
    return "OK"

  @cherrypy.expose
  def setBidPlayer(self, playerName, teamName):
    Utils.execute("""Truncate bid;
                      SET @pid = (SELECT pid from player WHERE player_name = %s);
                      SET @tid = (SELECT team_id from team WHERE teamname = %s);
                      
                      Insert into bid
                      SET bid = 0, pid = @pid, team_id = @tid;""",(playerName, teamName))
    return "OK"

  @cherrypy.expose
  def takeBreak(self):
    Utils.execute("""Truncate bid;""",())
    return "OK"
################################################################################
  @cherrypy.expose
  def logout(self):
    body = json.loads( cherrypy.request.body.read() )

    count = Utils.execute("DELETE FROM User_Session WHERE session_token = %s",(body["token"]))
    if(count > 0):
      return json.JSONEncoder().encode({
        "status" : Utils.status(0, "OK")
      })
    else:
      return json.JSONEncoder().encode({
        "status" : Utils.status(125, "Could not locate user with provided token")
      })

  @cherrypy.expose
  def register(self):
    body = json.loads( cherrypy.request.body.read() )

    count = Utils.execute("INSERT INTO User (username, password, email, salt) VALUES (%s, %s, %s, 'ABCDEFG')", (body['username'], body['password'], body['email']) )

    if(count == 1):
      return json.JSONEncoder().encode({
        "status" : Utils.status(0, "OK")
      })
    else:
      return json.JSONEncoder().encode({
        "status" : Utils.status(431, "Could not register user account.")
      })
              
  @cherrypy.expose
  def deleteAccount(self):
    body = json.loads( cherrypy.request.body.read() )
    
    count = Utils.execute("DELETE u FROM User_Session us LEFT JOIN User u ON us.user_id = u.user_id WHERE username = %s and password = %s",(body["username"], body["password"]))
    
    if(count > 0):
      return json.JSONEncoder().encode({
        "status" : Utils.status(0, "OK")
      })
    else:
      return json.JSONEncoder().encode({
        "status" : Utils.status(433, "Could not delete user account.")
      })
    
  @cherrypy.expose
  def get_configuration(self):
    length  = cherrypy.request.headers[ 'Content-Length' ]
    rawbody = cherrypy.request.body.read( int( length ) )
    body    = json.loads( rawbody )

    db = Utils.database()

    cur = db.cursor()
    cur.execute( "SELECT * FROM User_Session WHERE session_token = '%s'" % body["token"] )

    data = cur.fetchall()
    db.close()

    if not data: # There's a valid session
      return json.JSONEncoder().encode(
        {
            "status" : Utils.status(245, "You do not have a valid session token")
        } )

    return """
{
  "configuration": {
    "name": "Configuration 1"
    "width": 30,
    "height": 30,
    "widgetList": {
      "0": {
        "width": "5"
        "height": "5"
        "x": 0
        "y": 0
        "type": "table"
        "feed": {
          "type": "email"
          "data": {
            "0": {
              "sender": "Jack",
              "subject": "Test message 1"
            }
            "1": {
              "sender": "Bob",
              "subject": "Test message 2"
            }
            "2": {
              "sender": "Bill",
              "subject": "Test message 3"
            }
          }
        }
      }
      "1": {
        "width": "10"
        "height": "10"
        "x": 10
        "y": 10
        "type": "table"
        "feed": {
          "type": "twitter"
          "data": {
            "0": {
              "name": "Ted",
              "tweet": "Test tweet"
            }
            "1": {
              "name": "Kenny",
              "tweet": "Test tweet 2"
            }
            "2": {
              "name": "Neil",
              "tweet": "Test tweet 3"
            }
          }
        }
      }
    }
  },
  "status": {
    "code": "OK",
    "message": ""
  }
}"""
