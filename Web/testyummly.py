import requests
import urllib
class Yummly:
	s=requests.Session()
	base="http://api.yummly.com/v1/api/recipes"
	def setup(self,appid,appkey):
		self.s.headers.update({'X-Yummly-App-ID':appid,'X-Yummly-App-Key':appkey})
	def qsearch(self,ing):
		pms=urllib.quote("allowedCourse[]")+"="+urllib.quote("course^course-Main Dishes")
		for i in ing:
			pms=pms+"&"+urllib.quote("allowedIngredient[]")+"="+urllib.quote(i)
		print self.base+"?"+pms
		q=self.s.get(self.base+"?"+pms)
		print q.text
if __name__ == "__main__":
	y=Yummly()
	y.setup('d579507d','736c339e12b7a287c72c2de82313657e')
	y.qsearch([])
	#todo