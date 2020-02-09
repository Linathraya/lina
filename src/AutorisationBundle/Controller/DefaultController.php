<?php

namespace AutorisationBundle\Controller;

use Symfony\Bundle\FrameworkBundle\Controller\Controller;

class DefaultController extends Controller
{
    public function indexAction()
    {
        return $this->render('AutorisationBundle:Default:index.html.twig');
    }
}
